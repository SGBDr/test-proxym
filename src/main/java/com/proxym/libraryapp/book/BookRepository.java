package com.proxym.libraryapp.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private static Map<ISBN, Book> availableBooks = new HashMap<>();

    public void saveAll(List<Book> books){
        books.forEach(book -> availableBooks.put(book.getIsbn(), book));
    }

    public void emptyDb() {
        availableBooks = new HashMap<>();
    }

    public Optional<Book> find(long isbnCode) {
        Book book = availableBooks.get(ISBN.fromLong(isbnCode));

        return Optional.ofNullable(book);
    }

    public void remove(Book book) {
        availableBooks.remove(book.getIsbn());
    }

    public void save(Book book) {
        availableBooks.put(book.getIsbn(), book);
    }
}
