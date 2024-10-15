package com.proxym.libraryapp.borrow;

import com.proxym.libraryapp.book.ISBN;

import java.util.*;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BorrowRepository {
    // <userId, Borrow>
    private static final Map<Long, List<Borrow>> borrows = new HashMap<>();

    public void save(Borrow borrow){
        List<Borrow> borrowsOfMember = this.findBorrow(borrow.getMemberId());
        borrowsOfMember.add(borrow);

        borrows.put(borrow.getMemberId(), borrowsOfMember);
    }

    public void remove(Long memberId, ISBN isbnBook) {
        List<Borrow> borrowsOfMember = this.findBorrow(memberId);

        borrowsOfMember.removeIf(borrow -> borrow.haveSame(memberId, isbnBook));

        borrows.put(memberId, borrowsOfMember);
    }

    public List<Borrow> findBorrow(Long memberId) {
        return borrows.getOrDefault(memberId, new ArrayList<>());
    }

    public Optional<Borrow> findBorrow(Long memberId, ISBN isbnBook) {
        List<Borrow> borrowsOfMember = this.findBorrow(memberId);

        return borrowsOfMember.stream()
                .filter(borrow -> borrow.haveSame(memberId, isbnBook))
                .findFirst();
    }
}
