package com.furkanbayraktar.android.binding.binder;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.furkanbayraktar.android.binding.common.DataBinderAdapterListener;

import org.json.JSONObject;

/**
 * Created on 6/19/14.
 *
 * @author Furkan BAYRAKTAR
 */
public interface Binder {
    public void bindActivity(JSONObject object, Activity activity);
    public void bindActivity(Object object, Activity activity);
    public void bindActivity(JSONObject object, Activity activity, Integer rowLayoutId);
    public void bindActivity(Object object, Activity activity, Integer rowLayoutId);
    public void bindActivity(JSONObject object, Activity activity, Integer rowLayoutId, DataBinderAdapterListener listener);
    public void bindActivity(Object object, Activity activity, Integer rowLayoutId, DataBinderAdapterListener listener);
    public void bindView(JSONObject object, ViewGroup view, Context context);
    public void bindView(Object object, ViewGroup view, Context context);
    public void bindView(JSONObject object, ViewGroup view, Context context, Integer rowLayoutId);
    public void bindView(Object object, ViewGroup view, Context context, Integer rowLayoutId);
    public void bindView(JSONObject object, ViewGroup view, Context context, Integer rowLayoutId, DataBinderAdapterListener listener);
    public void bindView(Object object, ViewGroup view, Context context, Integer rowLayoutId, DataBinderAdapterListener listener);
}
