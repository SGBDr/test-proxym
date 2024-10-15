package com.proxym.libraryapp.library.paybookstrategy;

import com.proxym.libraryapp.member.money.Money;

public interface PayStrategy {
    Money mustPay(long keepBookDay, long offerDay);
}
