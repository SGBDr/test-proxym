package com.proxym.libraryapp.library;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proxym.libraryapp.book.Book;
import com.proxym.libraryapp.book.BookRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.proxym.libraryapp.member.Member;
import com.proxym.libraryapp.member.Resident;
import com.proxym.libraryapp.member.Student;
import com.proxym.libraryapp.member.money.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {
    private final double INITIAL_TAXED = 0.1;
    private final double SOME_MONEY_AMOUNT = 50;
    private final LocalDate TODAY_DATE = LocalDate.now();
    private final int SOME_15_NUMBER_OF_DAY = 15;
    private final LocalDate SOME_15_DAY_BEHIND_TODAY_DATE = LocalDate.now().minusDays(SOME_15_NUMBER_OF_DAY);
    private final int SOME_25_NUMBER_OF_DAY = 25;
    private final LocalDate SOME_25_DAY_BEHIND_TODAY_DATE = LocalDate.now().minusDays(SOME_25_NUMBER_OF_DAY);
    private final int SOME_75_NUMBER_OF_DAY = 75;
    private final LocalDate SOME_75_DAY_BEHIND_TODAY_DATE = LocalDate.now().minusDays(SOME_75_NUMBER_OF_DAY);
    private final int SOME_60_NUMBER_OF_DAY = 60;

    private Library library;
    private BookRepository bookRepository;
    private static List<Book> books;


    @BeforeEach
    void setup() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = JsonMapper.builder() // or different mapper for other format
                .addModule(new JavaTimeModule())
                .build();
        File booksJson = new File("src/test/resources/books.json");
        books = mapper.readValue(booksJson, new TypeReference<List<Book>>() {});

        library = new LibraryImp();
        bookRepository = new BookRepository();

        bookRepository.emptyDb();
        bookRepository.saveAll(books);
    }

    @Test
    void member_can_borrow_a_book_if_book_is_available() {
        // Given
        Member SOME_MEMBER = new Resident();
        Book expectedBook = books.get(0);

        // When
        Book actualBook = library.borrowBook(expectedBook.getIsbn().getIsbnCode(), SOME_MEMBER, TODAY_DATE);

        // Then
        Assertions.assertEquals(expectedBook, actualBook);
    }

    @Test
    void borrowed_book_is_no_longer_available() {
        // Given
        Book SOME_BOOK = books.get(0);
        Member SOME_MEMBER = new Resident();

        bookRepository.remove(SOME_BOOK);

        // When
        Book expectedReturn = library.borrowBook(SOME_BOOK.getIsbn().getIsbnCode(), SOME_MEMBER, TODAY_DATE);

        // Then
        Assertions.assertNull(expectedReturn);
    }

    @Test
    void residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
        // Given
        Member SOME_RESIDENT = new Resident();
        SOME_RESIDENT.setWallet(Money.fromDouble(SOME_MONEY_AMOUNT));
        Book SOME_BOOK = books.get(0);

        library.borrowBook(SOME_BOOK.getIsbn().getIsbnCode(), SOME_RESIDENT, SOME_15_DAY_BEHIND_TODAY_DATE);

        // When
        library.returnBook(SOME_BOOK, SOME_RESIDENT);

        Money expectedRest = Money.fromDouble(SOME_MONEY_AMOUNT);
        expectedRest.removeAmount(Money.fromDouble(SOME_15_NUMBER_OF_DAY * INITIAL_TAXED));

        // Then
        Assertions.assertEquals(expectedRest, SOME_RESIDENT.getWallet());
    }

    @Test
    void students_pay_10_cents_the_first_30days() {
        // Given
        int SOME_SCHOOL_YEAR = 2;
        Member SOME_STUDENT = new Student(SOME_SCHOOL_YEAR);
        SOME_STUDENT.setWallet(Money.fromDouble(SOME_MONEY_AMOUNT));
        Book SOME_BOOK = books.get(0);

        library.borrowBook(SOME_BOOK.getIsbn().getIsbnCode(), SOME_STUDENT, SOME_25_DAY_BEHIND_TODAY_DATE);

        // When
        library.returnBook(SOME_BOOK, SOME_STUDENT);

        Money expectedRest = Money.fromDouble(SOME_MONEY_AMOUNT);
        expectedRest.removeAmount(Money.fromDouble(SOME_25_NUMBER_OF_DAY * INITIAL_TAXED));

        // Then
        Assertions.assertEquals(expectedRest, SOME_STUDENT.getWallet());
    }

    @Test
    void students_in_1st_year_are_not_taxed_for_the_first_15days() {
        // Given
        int SOME_SCHOOL_YEAR = 1;
        Member SOME_STUDENT = new Student(SOME_SCHOOL_YEAR);
        SOME_STUDENT.setWallet(Money.fromDouble(SOME_MONEY_AMOUNT));
        Book SOME_BOOK = books.get(0);

        library.borrowBook(SOME_BOOK.getIsbn().getIsbnCode(), SOME_STUDENT, SOME_25_DAY_BEHIND_TODAY_DATE);

        // When
        library.returnBook(SOME_BOOK, SOME_STUDENT);

        Money expectedRest = Money.fromDouble(SOME_MONEY_AMOUNT);
        expectedRest.removeAmount(Money.fromDouble((SOME_25_NUMBER_OF_DAY - SOME_15_NUMBER_OF_DAY) * INITIAL_TAXED));

        // Then
        Assertions.assertEquals(expectedRest, SOME_STUDENT.getWallet());
    }

    @Test
    void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
        // Given
        double MAJ_TAXED = 0.2;
        Member SOME_RESIDENT = new Resident();
        SOME_RESIDENT.setWallet(Money.fromDouble(SOME_MONEY_AMOUNT));
        Book SOME_BOOK = books.get(0);

        library.borrowBook(SOME_BOOK.getIsbn().getIsbnCode(), SOME_RESIDENT, SOME_75_DAY_BEHIND_TODAY_DATE);

        // When
        library.returnBook(SOME_BOOK, SOME_RESIDENT);

        Money expectedRest = Money.fromDouble(SOME_MONEY_AMOUNT);
        expectedRest.removeAmount(Money.fromDouble(SOME_60_NUMBER_OF_DAY * INITIAL_TAXED + SOME_15_NUMBER_OF_DAY * MAJ_TAXED));

        // Then
        Assertions.assertEquals(expectedRest, SOME_RESIDENT.getWallet());
    }

    @Test
    void members_cannot_borrow_book_if_they_have_late_books() {
        // Given
        Member SOME_MEMBER = new Resident();
        SOME_MEMBER.setWallet(Money.fromDouble(SOME_MONEY_AMOUNT));
        Book SOME_BOOK1 = books.get(0);
        Book SOME_BOOK2 = books.get(1);

        library.borrowBook(SOME_BOOK1.getIsbn().getIsbnCode(), SOME_MEMBER, SOME_75_DAY_BEHIND_TODAY_DATE);

        // Then
        Assertions.assertThrows(HasLateBooksException.class, () -> {
            // When
            library.borrowBook(SOME_BOOK2.getIsbn().getIsbnCode(), SOME_MEMBER, SOME_75_DAY_BEHIND_TODAY_DATE);
        });
    }
}
