package com.proxym.libraryapp.library.laterreturnstrategy;

import java.time.LocalDate;

public interface LaterReturnStrategy {
    boolean isLate(LocalDate localDate);
}
