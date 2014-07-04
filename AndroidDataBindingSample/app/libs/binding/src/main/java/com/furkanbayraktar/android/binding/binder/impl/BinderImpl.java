package com.furkanbayraktar.android.binding.binder.impl;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.furkanbayraktar.android.binding.binder.Binder;
import com.furkanbayraktar.android.binding.common.DataBinderAdapter;
import com.furkanbayraktar.android.binding.common.DataBinderAdapterListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Created on 6/18/14.
 *
 * @author Furkan BAYRAKTAR
 */
public class BinderImpl implements Binder {

    @Override
    public void bindActivity(JSONObject object, Activity activity){
        ViewGroup v = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(object, v, activity.getApplicationContext());
    }

    @Override
    public void bindActivity(Object object, Activity activity){
        ViewGroup v = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(object, v, activity.getApplicationContext());
    }

    @Override
    public void bindActivity(JSONObject object, Activity activity, Integer rowLayoutId) {
        ViewGroup v = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(object, v, activity.getApplicationContext(), rowLayoutId);
    }

    @Override
    public void bindActivity(Object object, Activity activity, Integer rowLayoutId) {
        ViewGroup v = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(object, v, activity.getApplicationContext(), rowLayoutId);
    }

    @Override
    public void bindActivity(JSONObject object, Activity activity, Integer rowLayoutId, DataBinderAdapterListener listener) {
        ViewGroup v = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(object, v, activity.getApplicationContext(), rowLayoutId, listener);
    }

    @Override
    public void bindActivity(Object object, Activity activity, Integer rowLayoutId, DataBinderAdapterListener listener) {
        ViewGroup v = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(object, v, activity.getApplicationContext(), rowLayoutId, listener);
    }

    @Override
    public void bindView(JSONObject object, ViewGroup view, Context context){
        bindViewHelper(object,view,context, null, null);
    }

    @Override
    public void bindView(Object object, ViewGroup view, Context context){
        bindViewHelper(object,view,context, null, null);
    }

    @Override
    public void bindView(JSONObject object, ViewGroup view, Context context, Integer rowLayoutId) {
        bindViewHelper(object,view,context, rowLayoutId, null);
    }

    @Override
    public void bindView(Object object, ViewGroup view, Context context, Integer rowLayoutId) {
        bindViewHelper(object,view,context, rowLayoutId, null);
    }

    @Override
    public void bindView(JSONObject object, ViewGroup view, Context context, Integer rowLayoutId, DataBinderAdapterListener listener) {
        bindViewHelper(object,view,context, rowLayoutId, listener);
    }

    @Override
    public void bindView(Object object, ViewGroup view, Context context, Integer rowLayoutId, DataBinderAdapterListener listener) {
        bindViewHelper(object,view,context, rowLayoutId, listener);
    }

    private void bindViewHelper(Object object, ViewGroup view, Context context, Integer rowLayoutId, DataBinderAdapterListener listener){
        for (int i = 0; i < view.getChildCount(); i++) {
            View c = view.getChildAt(i);
            if(c instanceof ListView){
                listBinder(object,c,context,rowLayoutId,listener);
            }else if(c instanceof WebView){
                viewBinder(object, c, context);
            }else if(c instanceof ViewGroup){
                bindViewHelper(object, (ViewGroup) c, context, rowLayoutId, listener);
            }else{
                viewBinder(object, c, context);
            }
        }
    }

    private Object findResultWithTag(Object object, String tag){

        Object result = null;
        boolean negate = false;

        if(tag.charAt(0) == '!'){
            negate = true;
            tag = tag.substring(1);
        }

        if(tag.contains(":")){
            String path[] = tag.split(":");
            Object temp = object;
            for (int j = 0; j < path.length-1; j++) {
                if(object instanceof JSONObject){
                    try {
                        if(path[j].contains("[")){
                            try {
                                int indexOfOpenBracket = path[j].indexOf('[');
                                int indexOfCloseBracket = path[j].indexOf(']');
                                String arrayName = path[j].substring(0, indexOfOpenBracket);
                                int index = Integer.parseInt(path[j].substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                                temp = ((JSONObject) temp).getJSONArray(arrayName).get(index);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            temp = ((JSONObject) temp).getJSONObject(path[j]);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (ClassCastException e){
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    String methodName = null;
                    boolean isArray = false;
                    int index = -1;
                    if(path[j].contains("[")){
                        try{
                            int indexOfOpenBracket = path[j].indexOf('[');
                            int indexOfCloseBracket = path[j].indexOf(']');
                            String arrayName = path[j].substring(0, indexOfOpenBracket);
                            index = Integer.parseInt(path[j].substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                            String first = arrayName.substring(0, 1);
                            methodName = first.toUpperCase() + arrayName.substring(1);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        isArray = true;
                    }else{
                        String first = path[j].substring(0, 1);
                        methodName = first.toUpperCase() + path[j].substring(1);
                        isArray = false;
                    }

                    Method m = null;

                    if(methodName!=null) {

                        try {
                            m = temp.getClass().getDeclaredMethod("get"+methodName);
                        } catch (NoSuchMethodException e) {
                            try {
                                m = temp.getClass().getDeclaredMethod("is" + methodName);
                            }catch (NoSuchMethodException ex){
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (m != null) {
                        try {
                            if(!isArray) {
                                temp = m.invoke(temp);
                            }else{
                                temp = ((ArrayList<Object>)m.invoke(temp)).get(index);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            if(temp!=object) {
                if(object instanceof JSONObject){
                    try {
                        String lastPath = path[path.length - 1];
                        if(lastPath.contains("[")){
                            try {
                                int indexOfOpenBracket = lastPath.indexOf('[');
                                int indexOfCloseBracket = lastPath.indexOf(']');
                                String arrayName = lastPath.substring(0, indexOfOpenBracket);
                                int index = Integer.parseInt(lastPath.substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                                result = ((JSONObject) temp).getJSONArray(arrayName).get(index);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            result = ((JSONObject) temp).get(lastPath);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ClassCastException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    String lastPath = path[path.length - 1];
                    if(lastPath.contains("[")){
                        try {
                            int indexOfOpenBracket = lastPath.indexOf('[');
                            int indexOfCloseBracket = lastPath.indexOf(']');
                            String arrayName = lastPath.substring(0, indexOfOpenBracket);
                            int index = Integer.parseInt(lastPath.substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                            String first = arrayName.substring(0, 1);
                            String methodName = first.toUpperCase() + arrayName.substring(1);
                            try {
                                result = ((ArrayList<Object>)temp.getClass().getDeclaredMethod("get"+methodName).invoke(temp)).get(index);
                            } catch (NoSuchMethodException e) {
                                try {
                                    result = ((ArrayList<Object>)temp.getClass().getDeclaredMethod("is"+methodName).invoke(temp)).get(index);
                                }catch (NoSuchMethodException ex){
                                    e.printStackTrace();
                                }
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        String first = lastPath.substring(0, 1);
                        String methodName = first.toUpperCase() + lastPath.substring(1);
                        try {
                            result = temp.getClass().getDeclaredMethod("get" + methodName).invoke(temp);
                        } catch (NoSuchMethodException e) {
                            try{
                                result = temp.getClass().getDeclaredMethod("is" + methodName).invoke(temp);
                            }catch (NoSuchMethodException ex){
                                ex.printStackTrace();
                            }catch (IllegalAccessException ex){
                                ex.printStackTrace();
                            } catch (InvocationTargetException ex) {
                                ex.printStackTrace();
                            }
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        }else{
            if(tag.contains("[")){
                int indexOfOpenBracket = tag.indexOf('[');
                int indexOfCloseBracket = tag.indexOf(']');
                String arrayName = tag.substring(0, indexOfOpenBracket);
                int index = Integer.parseInt(tag.substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                if(object instanceof JSONObject){
                    try {
                        result = ((JSONObject) object).getJSONArray(arrayName).get(index);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ClassCastException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    String first = arrayName.substring(0, 1);
                    String methodName = first.toUpperCase()+arrayName.substring(1);
                    try {
                        result = ((ArrayList<Object>)object.getClass().getDeclaredMethod("get"+methodName).invoke(object)).get(index);
                    } catch (NoSuchMethodException e) {
                        try{
                            result = ((ArrayList<Object>)object.getClass().getDeclaredMethod("is"+methodName).invoke(object)).get(index);
                        }catch (NoSuchMethodException ex){

                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                if(object instanceof JSONObject){
                    try {
                        result = ((JSONObject) object).get(tag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ClassCastException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    String first = tag.substring(0, 1);
                    String methodName = first.toUpperCase()+tag.substring(1);
                    try {
                        result = object.getClass().getDeclaredMethod("get"+methodName).invoke(object);
                    } catch (NoSuchMethodException e) {
                        try{
                            result = object.getClass().getDeclaredMethod("is"+methodName).invoke(object);
                        }catch (NoSuchMethodException ex){
                            ex.printStackTrace();
                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        if(negate){
            if(result instanceof Boolean){
                Boolean b = (Boolean)result;
                return  !b;
            }
        }

        return result;
    }

    private void viewBinder(Object object, View c, Context context){
        String tag = (String) c.getTag();
        if(tag == null){
            return;
        }
        Object obj = findResultWithTag(object,tag);

        if(obj == null){
            return;
        }

        if(obj instanceof Boolean){
            Boolean b = (Boolean) obj;
            if (b){
                c.setVisibility(View.VISIBLE);
            }else{
                c.setVisibility(View.GONE);
            }
        }else{
            String result = String.valueOf(obj);

            if(c instanceof TextView) {
                ((TextView) c).setText(result);
            }else if(c instanceof ImageView){
                Picasso.with(context).load(result).into((ImageView)c);
            }else if(c instanceof WebView){
                ((WebView) c).setWebChromeClient(new WebChromeClient());
                ((WebView) c).loadUrl(result);
            }
        }
    }

    private void listBinder(Object object, View c, Context context, Integer rowLayoutId, DataBinderAdapterListener listener){
        if(rowLayoutId==null){
            return;
        }
        String tag = (String) c.getTag();
        if(tag == null){
            return;
        }
        Object result = findResultWithTag(object, tag);
        if(result == null){
            return;
        }
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (result instanceof JSONArray) {
                JSONArray array = (JSONArray) result;
                for (int i = 0; i < array.length(); i++) {
                    try {
                        list.add(array.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                list = (ArrayList<Object>) result;
            }
        }catch (ClassCastException e){
            e.printStackTrace();
        }

        ListView listView = (ListView) c;

        DataBinderAdapter adapter;
        if(listener==null){
            adapter = new DataBinderAdapter(context,rowLayoutId, list);
        }else{
            adapter = new DataBinderAdapter(context,rowLayoutId, list,listener);
        }

        listView.setAdapter(adapter);
    }
}
