package com.proxym.libraryapp.member.money;

public class WrongAmountException extends RuntimeException {
    public WrongAmountException(String amount) {
        super(String.format("Amount not supported : %s", amount));
    }
}
