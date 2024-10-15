package com.proxym.libraryapp.member;

import com.proxym.libraryapp.library.Library;
import com.proxym.libraryapp.member.money.Money;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    private final long DEFAULT_OFFER_DAY_FOR_FIRST_MEMBER = 0;
    /**
     * An initial sum of money the member has
     */
    private Money wallet;

    private static Long counter = 0L;

    private final Long memberId;

    public Member() {
        this.memberId = ++counter;
    }

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param money the amount suppose to pay for the book
     */
    public void payAmount(Money money) {
        this.wallet.removeAmount(money);
    }

    public void setWallet(Money money) {
        this.wallet = money;
    }

    public Long getMemberId() {
        return memberId;
    }

    public long getOfferDay() {
        return DEFAULT_OFFER_DAY_FOR_FIRST_MEMBER;
    }

    public Money getWallet() {
        return this.wallet;
    }
}
