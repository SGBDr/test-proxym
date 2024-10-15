package com.proxym.libraryapp.book;

import java.util.Objects;

public class ISBN {
    private long isbnCode;

    private ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public ISBN() {}

    public long getIsbnCode() {
        return isbnCode;
    }

    public static ISBN fromLong(long isbnCode) {
        return new ISBN(isbnCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return isbnCode == isbn.isbnCode;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbnCode);
    }

    @Override
    public String toString() {
        return "ISBN{" +
                "isbnCode=" + isbnCode +
                '}';
    }
}
