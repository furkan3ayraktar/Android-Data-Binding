package com.furkanbayraktar.databinding.exception;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/18/14.
 * Copyright Valensas A.S.
 */
public class FunctionException extends RuntimeException{
    public FunctionException() {
        super("Function not found.");
    }

    public FunctionException(String detailMessage) {
        super(detailMessage);
    }

    public FunctionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FunctionException(Throwable throwable) {
        super("Function not found.", throwable);
    }
}
