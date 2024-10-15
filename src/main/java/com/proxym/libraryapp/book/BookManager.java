package com.proxym.libraryapp.book;

import java.util.Optional;

public class BookManager {
    private final BookRepository bookRepository;

    public BookManager() {
        this.bookRepository = new BookRepository();
    }

    public Book getBook(long isbnCode) {
        Optional<Book> bookOptional = this.bookRepository.find(isbnCode);

        return bookOptional.orElseThrow(() -> new NotFoundBookException(isbnCode));
    }

    public boolean isInStore(long isbnCode) {
        Optional<Book> bookOptional = this.bookRepository.find(isbnCode);

        return bookOptional.isPresent();
    }

    public void addBook(Book book) {
        this.bookRepository.save(book);
    }

    public void removeBook(Book book) {
        this.bookRepository.remove(book);
    }
}
