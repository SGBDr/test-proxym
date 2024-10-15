package com.proxym.libraryapp.member.money;

public class WrongAmountOperationException extends RuntimeException {
    public WrongAmountOperationException(String amount) {
        super(String.format("New amount not supported : %s", amount));
    }
}
