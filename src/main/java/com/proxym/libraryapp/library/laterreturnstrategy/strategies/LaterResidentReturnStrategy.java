package com.proxym.libraryapp.library.laterreturnstrategy.strategies;

import com.proxym.libraryapp.library.laterreturnstrategy.LaterReturnStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LaterResidentReturnStrategy implements LaterReturnStrategy {
    private static final long MAXIMUM_KEEPING_DAY = 60L;

    @Override
    public boolean isLate(LocalDate localDate) {
        return ChronoUnit.DAYS.between(localDate, LocalDate.now()) > MAXIMUM_KEEPING_DAY;
    }
}
