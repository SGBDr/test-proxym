package com.proxym.libraryapp.library.paybookstrategy.strategies;

import com.proxym.libraryapp.library.paybookstrategy.PayStrategy;
import com.proxym.libraryapp.member.money.Money;

public class ResidentPayStrategy implements PayStrategy {
    private final int MAXIMUM_KEEPING_DAY = 60;
    private final double INITIAL_PRICE = 0.1;
    private final double EXTRA_PRICE = 0.2;

    @Override
    public Money mustPay(long keepBookDay, long offerDay) {
        keepBookDay = keepBookDay > offerDay ? keepBookDay - offerDay : keepBookDay;
        long extraDay = keepBookDay > MAXIMUM_KEEPING_DAY ? (keepBookDay - MAXIMUM_KEEPING_DAY) : 0;
        keepBookDay -= extraDay;

        return Money.fromDouble(extraDay * EXTRA_PRICE + keepBookDay * INITIAL_PRICE);
    }
}
