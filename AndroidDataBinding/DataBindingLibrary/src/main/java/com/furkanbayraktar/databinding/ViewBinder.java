package com.furkanbayraktar.databinding;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.furkanbayraktar.databinding.common.resolver.FunctionResolver;
import com.furkanbayraktar.databinding.common.resolver.AssignmentResolver;
import com.furkanbayraktar.databinding.common.parser.Parser;
import com.furkanbayraktar.databinding.common.resolver.VisibilityResolver;
import com.furkanbayraktar.databinding.logging.Logger;
import com.furkanbayraktar.databinding.model.BasePOJO;

import java.util.HashMap;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 *
 */
public class ViewBinder {

    public static final String TAG = "AndroidDataBinder";

    private static ViewBinder instance;

    private boolean logging;

    private ViewBinder(){}

    public static ViewBinder getInstance(){
        if(instance == null){
            instance = new ViewBinder();
        }
        return instance;
    }

    public void setLoggingEnabled(boolean enabled){
        logging = enabled;
        if(enabled){
            Logger.info("Logging enabled.");
        } else {
            Logger.info("Logging disabled.");
        }
    }

    public boolean isLoggingEnabled(){
        return logging;
    }

    public void bindView(BasePOJO object, View view, Context context, Integer rowLayoutId, Object actionObject){
        if(object == null){
            return;
        }
        if(isLoggingEnabled()) {
            Logger.info("Binding view...");
        }
        if(view instanceof ViewGroup){
            bindViewHelper(object, view, context, rowLayoutId, actionObject);
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                bindView(object, ((ViewGroup) view).getChildAt(i), context, rowLayoutId, actionObject);
            }
        }else{
            bindViewHelper(object, view, context, rowLayoutId, actionObject);
        }
    }

    public void bindView(BasePOJO object, View view, Context context, Integer rowLayoutId){
        bindView(object, view, context, rowLayoutId, null);
    }

    public void bindView(BasePOJO object, Activity activity, Integer rowLayoutId){
        View view = activity.findViewById(android.R.id.content);

        bindView(object, view, activity.getApplicationContext(), rowLayoutId, activity);
    }

    public void bindView(BasePOJO object, View view, Context context){
        bindView(object, view, context, null, null);
    }

    public void bindView(BasePOJO object, View view, Context context, Object actionObject){
        bindView(object, view, context, null, actionObject);
    }

    public void bindView(BasePOJO object, Activity activity){
        View view = activity.findViewById(android.R.id.content);

        bindView(object, view, activity.getApplicationContext(), null, activity);
    }

    private void bindViewHelper(BasePOJO object, View view, Context context, Integer rowLayoutId, Object actionObject){

        String viewTag = (String) view.getTag();

        if(viewTag == null){
            return;
        }

        HashMap<String, String> parsed = Parser.getInstance().parse(viewTag);

        if(parsed != null) {
            String tag = parsed.get("assign");

            if (tag != null) {
                AssignmentResolver.getInstance().resolve(view, tag, object, context, rowLayoutId, actionObject);
            }

            tag = parsed.get("visible");

            if (tag != null) {
                VisibilityResolver.getInstance().resolve(view, tag, object);
            }

            tag = parsed.get("func");

            if (tag != null) {
                FunctionResolver.getInstance().resolve(view, tag, object, actionObject);
            }
        } else {
            Logger.info("Cannot parse view tag.");
        }
    }
}
