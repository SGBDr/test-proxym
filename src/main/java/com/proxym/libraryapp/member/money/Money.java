package com.proxym.libraryapp.member.money;

import java.util.Objects;

public class Money {
    private double amount;

    private Money(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public void removeAmount(Money money) {
        double newAmount = this.amount - money.getAmount();

        if(newAmount < 0)
            throw new WrongAmountOperationException(String.valueOf(newAmount));

        this.amount = newAmount;
    }

    public static Money fromDouble(double amount) {
        if(amount < 0)
            throw new WrongAmountException(String.valueOf(amount));

        return new Money(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(amount, money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                '}';
    }
}
