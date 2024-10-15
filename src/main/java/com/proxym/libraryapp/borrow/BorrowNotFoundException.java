package com.proxym.libraryapp.borrow;

public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(String cl) {
        super(String.format("Borrow of the book was not found : %s", cl));
    }
}
