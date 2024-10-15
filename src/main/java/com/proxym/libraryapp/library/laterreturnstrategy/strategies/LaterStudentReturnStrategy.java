package com.proxym.libraryapp.library.laterreturnstrategy.strategies;

import com.proxym.libraryapp.library.laterreturnstrategy.LaterReturnStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LaterStudentReturnStrategy implements LaterReturnStrategy {
    private static long MAXIMUM_KEEPING_DAY = 30L;

    @Override
    public boolean isLate(LocalDate localDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), localDate) > MAXIMUM_KEEPING_DAY;
    }
}
