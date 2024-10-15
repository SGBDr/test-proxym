package com.proxym.libraryapp.library.paybookstrategy.strategies;

import com.proxym.libraryapp.library.paybookstrategy.PayStrategy;
import com.proxym.libraryapp.member.money.Money;

public class StudentPayStrategy implements PayStrategy {
    private final double PRICE = 0.1;

    @Override
    public Money mustPay(long keepBookDay, long offerDay) {
        keepBookDay = keepBookDay > offerDay ? keepBookDay - offerDay : 0;

        return Money.fromDouble(keepBookDay * PRICE);
    }
}
