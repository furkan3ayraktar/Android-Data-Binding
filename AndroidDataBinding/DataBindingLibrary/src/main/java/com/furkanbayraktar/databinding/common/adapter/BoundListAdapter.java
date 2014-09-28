package com.furkanbayraktar.databinding.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.furkanbayraktar.databinding.ViewBinder;
import com.furkanbayraktar.databinding.exception.UnsupportedObjectException;
import com.furkanbayraktar.databinding.logging.Logger;
import com.furkanbayraktar.databinding.model.BasePOJO;

import java.util.List;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/18/14.
 * Copyright Valensas A.S.
 */
public class BoundListAdapter extends ArrayAdapter{

    private int layoutId;
    private Object[] objects;
    private Object actionObject;

    public BoundListAdapter(Context context, int resource, List objects, Object actionObject) {
        super(context, resource);

        this.objects = objects.toArray();
        this.layoutId = resource;
        this.actionObject = actionObject;

        Logger.info("A BoundListAdapter object is created.");
    }

    public BoundListAdapter(Context context, int resource, Object[] objects, Object actionObject) {
        super(context, resource);

        this.objects = objects;
        this.layoutId = resource;
        this.actionObject = actionObject;

        Logger.info("A BoundListAdapter object is created.");
    }

    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId,null);
        }

        Object object = objects[position];

        if(object instanceof BasePOJO) {
            Logger.info("Binding for row at position " + position + " started.");
            ViewBinder.getInstance().bindView((BasePOJO) object, convertView, getContext(), actionObject);
            Logger.info("Binding for row at position " + position + " completed.");
        }else{
            UnsupportedObjectException ex = new UnsupportedObjectException();
            Logger.error("Unsupported object for ListView row.", ex);
        }

        return convertView;
    }

}
