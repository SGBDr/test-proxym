package com.proxym.libraryapp.borrow;

import com.proxym.libraryapp.book.*;
import com.proxym.libraryapp.library.HasLateBooksException;
import com.proxym.libraryapp.library.laterreturnstrategy.LaterReturnStrategy;
import com.proxym.libraryapp.library.laterreturnstrategy.LaterReturnStrategyCreator;
import com.proxym.libraryapp.member.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowManager {
    private final BookManager bookManager;
    private final BorrowRepository borrowRepository;

    public BorrowManager() {
        this.borrowRepository = new BorrowRepository();
        this.bookManager = new BookManager();
    }

    public Borrow findBorrow(Long memberId, ISBN isbnBook) {
        Optional<Borrow> optionalBorrow = this.borrowRepository.findBorrow(memberId, isbnBook);

        if(optionalBorrow.isPresent())
            return optionalBorrow.get();

        throw new BorrowNotFoundException(isbnBook.toString());
    }

    public boolean canBorrow(long isbnCode, Member member) {
        if(haveLaterReturnBook(member))
            throw new HasLateBooksException();

        return this.bookManager.isInStore(isbnCode);
    }

    private boolean haveLaterReturnBook(Member member) {
        List<Borrow> borrows = this.borrowRepository.findBorrow(member.getMemberId());
        LaterReturnStrategy strategy = LaterReturnStrategyCreator.getStrategy(member.getClass());

        for(Borrow borrow: borrows)
            if(strategy.isLate(borrow.getBorrowDate()))
                return true;

        return false;
    }

    public Book borrow(long isbnCode, Member member, LocalDate borrowedAt) {
        this.borrowRepository.save(
                new Borrow(borrowedAt, ISBN.fromLong(isbnCode), member.getMemberId())
        );

        return this.bookManager.getBook(isbnCode);
    }

    public void remove(Borrow borrow) {
        this.borrowRepository.remove(borrow.getMemberId(), borrow.getIsbnBook());
    }
}
