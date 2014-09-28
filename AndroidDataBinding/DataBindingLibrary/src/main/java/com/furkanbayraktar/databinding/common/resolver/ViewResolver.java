package com.furkanbayraktar.databinding.common.resolver;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.furkanbayraktar.databinding.common.adapter.BoundListAdapter;
import com.furkanbayraktar.databinding.exception.UnsupportedViewException;
import com.furkanbayraktar.databinding.logging.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/18/14.
 * Copyright Valensas A.S.
 */
public class ViewResolver {

    private static ViewResolver instance;

    private ViewResolver(){
        Logger.info("ViewResolver object created.");
    }

    public static ViewResolver getInstance(){
        if(instance == null){
            instance = new ViewResolver();
        }
        return instance;
    }

    public void resolve(View view, Object value, Context context, Integer rowLayoutId, Object actionObject){

        try {
            Logger.info("Resolving view...");

            if(view instanceof CompoundButton){
                if(value == null){
                    ((CompoundButton) view).setChecked(false);
                }

                if(value instanceof String){
                    if(value.equals("")){
                        ((CompoundButton) view).setChecked(false);
                    }else{
                        ((CompoundButton) view).setChecked(true);
                    }
                }else if(value instanceof Boolean){
                    ((CompoundButton) view).setChecked((Boolean) value);
                }else if(value instanceof Number){
                    if(value.equals(0)){
                        ((CompoundButton) view).setChecked(false);
                    }else{
                        ((CompoundButton) view).setChecked(true);
                    }
                }else{
                    ((CompoundButton) view).setChecked(true);
                }
            }else if(view instanceof ImageView){
                Picasso.with(context).load(String.valueOf(value)).into((ImageView) view);
            }else if(view instanceof WebView){
                ((WebView) view).loadUrl(String.valueOf(value));
            }else if(view instanceof ListView){
                if(rowLayoutId == null){
                    return;
                }
                if(value instanceof List){
                    BoundListAdapter adapter = new BoundListAdapter(context, rowLayoutId, (List) value,actionObject);
                    ((ListView) view).setAdapter(adapter);
                }else if(value instanceof Object[]) {
                    BoundListAdapter adapter = new BoundListAdapter(context, rowLayoutId, (Object[]) value, actionObject);
                    ((ListView) view).setAdapter(adapter);
                }else{
                    Logger.info("Given value for the ListView is not a List or Array");
                }
            }else if(view instanceof TextView){
                ((TextView) view).setText(String.valueOf(value));
            }else{
                throw new UnsupportedViewException(view.getClass());
            }

            Logger.info("View resolved successfully.");
        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
    }
}
