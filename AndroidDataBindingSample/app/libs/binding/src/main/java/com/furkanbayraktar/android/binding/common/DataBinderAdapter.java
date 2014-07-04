package com.furkanbayraktar.android.binding.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.furkanbayraktar.android.binding.BindingManager;

import java.util.ArrayList;

/**
 * Created on 6/19/14.
 *
 * @author Furkan BAYRAKTAR
 */
public class DataBinderAdapter extends ArrayAdapter<Object> {

    private int layoutId;
    private ArrayList<Object> objects;
    private DataBinderAdapterListener listener;

    public DataBinderAdapter(Context context, int resource, ArrayList<Object> objects) {
        super(context, resource);

        this.objects = objects;
        this.layoutId = resource;
    }

    public DataBinderAdapter(Context context, int resource, ArrayList<Object> objects, DataBinderAdapterListener listener) {
        super(context, resource);

        this.objects = objects;
        this.layoutId = resource;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId,null);
        }

        Object object = objects.get(position);
        if(convertView instanceof ViewGroup) {
            BindingManager.getBinder().bindView(object, (ViewGroup)convertView, getContext());
            if(listener!=null){
                listener.postViewBind(position,(ViewGroup)convertView);
            }
        }

        return convertView;
    }
}
