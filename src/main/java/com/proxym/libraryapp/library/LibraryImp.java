package com.proxym.libraryapp.library;

import com.proxym.libraryapp.book.Book;
import com.proxym.libraryapp.book.BookManager;
import com.proxym.libraryapp.borrow.Borrow;
import com.proxym.libraryapp.borrow.BorrowManager;
import com.proxym.libraryapp.library.paybookstrategy.PayBookStrategyCreator;
import com.proxym.libraryapp.library.paybookstrategy.PayStrategy;
import com.proxym.libraryapp.member.Member;
import com.proxym.libraryapp.member.money.Money;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryImp implements Library {
    private final BorrowManager borrowManager;
    private final BookManager bookManager;

    public LibraryImp() {
        this.borrowManager = new BorrowManager();
        this.bookManager = new BookManager();
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        Book book = null;

        if(borrowManager.canBorrow(isbnCode, member)) {
            book = borrowManager.borrow(isbnCode, member, borrowedAt);
            bookManager.removeBook(book);
        }

        return book;
    }

    @Override
    public void returnBook(Book book, Member member) {
        PayStrategy strategy = PayBookStrategyCreator.getStrategy(member.getClass());
        Borrow borrow = borrowManager.findBorrow(member.getMemberId(), book.getIsbn());

        Money money = strategy.mustPay(dayToThisDay(borrow.getBorrowDate()), member.getOfferDay());

        member.payAmount(money);
        borrowManager.remove(borrow);
        bookManager.addBook(book);
    }

    private Long dayToThisDay(LocalDate date) {
        return ChronoUnit.DAYS.between(date, LocalDate.now());
    }
}
