package com.furkanbayraktar.databinding.exception;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 * Copyright Valensas A.S.
 */
public class POJOParseException extends RuntimeException{

    public POJOParseException() {
        super("Field with given name did not found!");
    }

    public POJOParseException(String detailMessage) {
        super(detailMessage);
    }

    public POJOParseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public POJOParseException(Throwable throwable) {
        super("Field with given name did not found!", throwable);
    }
}
