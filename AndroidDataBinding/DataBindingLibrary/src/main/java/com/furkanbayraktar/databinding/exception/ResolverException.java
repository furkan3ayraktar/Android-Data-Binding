package com.furkanbayraktar.databinding.exception;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/16/14.
 *
 */
public class ResolverException extends RuntimeException{

    public ResolverException() {
        super("Resolver cannot resolve tag from given object!");
    }

    public ResolverException(String detailMessage) {
        super(detailMessage);
    }

    public ResolverException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ResolverException(Throwable throwable) {
        super("Resolver cannot resolve tag from given object!", throwable);
    }

}
