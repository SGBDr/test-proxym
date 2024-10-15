package com.proxym.libraryapp.book;

public class NotFoundBookException extends RuntimeException {
    public NotFoundBookException(long isbn) {
        super(String.format("Book with identifier %s was not found", isbn));
    }
}
