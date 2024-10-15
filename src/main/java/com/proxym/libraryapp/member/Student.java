package com.proxym.libraryapp.member;

public class Student extends Member {
    private final long OFFER_DAY_FOR_FIRST_YEAR_STUDENT = 15;
    private final int schoolYear;

    public Student(int schoolYear) {
        super();
        this.schoolYear = schoolYear;
    }

    private boolean isInFirstYear() {
        return this.schoolYear == 1;
    }

    public long getOfferDay() {
        return isInFirstYear() ? OFFER_DAY_FOR_FIRST_YEAR_STUDENT : 0;
    }
}
