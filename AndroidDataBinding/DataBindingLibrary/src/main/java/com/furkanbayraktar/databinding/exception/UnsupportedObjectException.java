package com.furkanbayraktar.databinding.exception;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/18/14.
 * Copyright Valensas A.S.
 */
public class UnsupportedObjectException extends RuntimeException {

    public UnsupportedObjectException() {
        super("Object must be type of BasePOJO.");
    }

    public UnsupportedObjectException(String detailMessage) {
        super(detailMessage);
    }

    public UnsupportedObjectException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnsupportedObjectException(Throwable throwable) {
        super("Object must be type of BasePOJO.", throwable);
    }
}
