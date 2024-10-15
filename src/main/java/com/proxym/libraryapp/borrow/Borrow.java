package com.proxym.libraryapp.borrow;

import com.proxym.libraryapp.book.ISBN;

import java.time.LocalDate;

public class Borrow {
    private final LocalDate borrowDate;
    private final ISBN isbnBook;
    private final Long memberId;

    public Borrow(LocalDate borrowDate, ISBN isbnBook, Long memberId) {
        this.borrowDate = borrowDate;
        this.isbnBook = isbnBook;
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public ISBN getIsbnBook() {
        return this.isbnBook;
    }

    public LocalDate getBorrowDate() {
        return this.borrowDate;
    }

    public boolean haveSame(Long memberId, ISBN isbnBook) {
        return this.memberId.equals(memberId) && this.isbnBook.equals(isbnBook);
    }
}
