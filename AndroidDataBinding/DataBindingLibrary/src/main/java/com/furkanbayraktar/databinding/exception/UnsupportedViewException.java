package com.furkanbayraktar.databinding.exception;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/18/14.
 * Copyright Valensas A.S.
 */
public class UnsupportedViewException extends RuntimeException{

    public UnsupportedViewException(Class clazz) {
        super("This view type is not supported by binder: " + clazz.getName());
    }

    public UnsupportedViewException(String detailMessage) {
        super(detailMessage);
    }

    public UnsupportedViewException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnsupportedViewException(Class clazz, Throwable throwable) {
        super("This view type is not supported by binder: " + clazz.getName(), throwable);
    }
}
