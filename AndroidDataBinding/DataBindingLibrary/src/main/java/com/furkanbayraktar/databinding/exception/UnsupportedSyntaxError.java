package com.furkanbayraktar.databinding.exception;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 * Copyright Valensas A.S.
 */
public class UnsupportedSyntaxError extends RuntimeException{

    public UnsupportedSyntaxError(){
        super("Wrong syntax detected in the tag.");
    }

    public UnsupportedSyntaxError(String detailMessage) {
        super(detailMessage);
    }

    public UnsupportedSyntaxError(Throwable throwable) {
        super("Wrong syntax detected in the tag.", throwable);
    }

    public UnsupportedSyntaxError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
