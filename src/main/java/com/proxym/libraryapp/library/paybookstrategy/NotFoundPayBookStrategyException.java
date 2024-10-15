package com.proxym.libraryapp.library.paybookstrategy;

public class NotFoundPayBookStrategyException extends RuntimeException {
    public NotFoundPayBookStrategyException(String cl) {
        super(String.format("No PayBookStrategy found member %s", cl));
    }
}
