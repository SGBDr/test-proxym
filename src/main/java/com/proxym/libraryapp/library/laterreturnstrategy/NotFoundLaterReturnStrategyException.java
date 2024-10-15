package com.proxym.libraryapp.library.laterreturnstrategy;

public class NotFoundLaterReturnStrategyException extends RuntimeException {
    public NotFoundLaterReturnStrategyException(String cl) {
        super(String.format("No LaterReturnStrategy found member %s", cl));
    }
}
