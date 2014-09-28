package com.furkanbayraktar.databinding.common.resolver;

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
public class VisibilityResolver {

    private static VisibilityResolver instance;

    private VisibilityResolver(){
        Logger.info("VisibilityResolver object created.");
    }

    public static VisibilityResolver getInstance(){
        if(instance == null){
            instance = new VisibilityResolver();
        }
        return instance;
    }

    public void resolve(View view, String tag, BasePOJO object){

        try {
            Logger.info("Resolving views for visibility...");

            BigDecimal result = ExpressionEvaluator.getInstance().evaluate(tag, object);

            if (result != null) {
                if (result.equals(new BigDecimal(0))) {
                    view.setVisibility(View.GONE);
                } else if (result.equals(new BigDecimal(1))) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    throw new ResolverException("Expected results are 0 or 1. Found: " + result);
                }
            } else {
                ResolverException ex = new ResolverException("Cannot evaluate expression.");
                Logger.error("Cannot evaluate expression.", ex);
            }

            Logger.info("Views resolved successfully.");
        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
    }
}
