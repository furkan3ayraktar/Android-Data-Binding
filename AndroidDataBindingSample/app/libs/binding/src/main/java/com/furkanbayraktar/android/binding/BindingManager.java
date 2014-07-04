package com.furkanbayraktar.android.binding;

import com.furkanbayraktar.android.binding.binder.Binder;
import com.furkanbayraktar.android.binding.binder.impl.BinderImpl;

/**
 * Created on 6/19/14.
 *
 * @author Furkan BAYRAKTAR
 */
public class BindingManager {

    private static com.furkanbayraktar.android.binding.binder.Binder binder;

    public static com.furkanbayraktar.android.binding.binder.Binder getBinder(){
        if(binder==null){
            binder = new BinderImpl();
        }
        return binder;
    }

    private BindingManager(){}
}
