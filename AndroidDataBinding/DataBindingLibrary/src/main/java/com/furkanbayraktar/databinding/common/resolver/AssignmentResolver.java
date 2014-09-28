package com.furkanbayraktar.databinding.common.resolver;

import android.content.Context;
import android.view.View;
import com.furkanbayraktar.databinding.common.parser.ExpressionEvaluator;
import com.furkanbayraktar.databinding.exception.ResolverException;
import com.furkanbayraktar.databinding.logging.Logger;
import com.furkanbayraktar.databinding.model.BasePOJO;

import java.math.BigDecimal;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 *
 */
public class AssignmentResolver {

    private static AssignmentResolver instance;

    private AssignmentResolver(){
        Logger.info("AssignmentResolver object created.");
    }

    public static AssignmentResolver getInstance(){
        if(instance == null){
            instance = new AssignmentResolver();
        }
        return instance;
    }

    public void resolve(View view, String tag, BasePOJO object, Context context, Integer rowLayoutId, Object actionObject){

        try {
            Logger.info("Resolving assignments for tag: " + tag);

            if (tag.contains("(")) {
                Logger.info("Assignment contains expression. Evaluating expression...");

                BigDecimal result = ExpressionEvaluator.getInstance().evaluate(tag, object);

                if (result != null) {
                    if (result.equals(new BigDecimal(0))) {
                        ViewResolver.getInstance().resolve(view, "0", context, rowLayoutId, actionObject);
                    } else if (result.equals(new BigDecimal(1))) {
                        ViewResolver.getInstance().resolve(view, "1", context, rowLayoutId, actionObject);
                    } else {
                        ResolverException ex = new ResolverException("Expected results are 0 or 1. Found: " + result);
                        Logger.error("Unexpected result.", ex);
                    }
                } else {
                    ResolverException ex = new ResolverException("Cannot evaluate expression.");
                    Logger.error("Unsupported expression syntax.", ex);
                }

                Logger.info("Evaluation completed.");
            } else {
                Object value = PathResolver.getInstance().resolve(tag, object);

                if (value == null) {
                    ResolverException ex = new ResolverException();
                    Logger.warn("Cannot resolve value for tag:" + tag, ex);
                }

                Logger.info("Value corresponding to given tag has been found. Assigning to view...");

                ViewResolver.getInstance().resolve(view, value, context, rowLayoutId, actionObject);

                Logger.info("View assignment completed.");
            }
        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
    }

}
