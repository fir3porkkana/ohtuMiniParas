/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class BookTest {

    public BookTest() {
    }

    @Test
    public void constructorWorksAsExpected() {
        Book book = new Book("Kirja", "Jaska J");
        assertEquals("Kirja", book.getTitle());
        assertEquals("Jaska J", book.getAuthor());
    }

    @Test
    public void isEmptyWorksEmptyAuthor() {
        Book book = new Book("Kirja", "");
        assertEquals(true, book.isEmpty());
    }

    @Test
    public void isEmptyWorksEmptyTitle() {
        Book book = new Book("", "Jaska J");
        assertEquals(true, book.isEmpty());
    }

    @Test
    public void isEmptyWorksBothEmpty() {
        Book book = new Book("", "");
        assertEquals(true, book.isEmpty());
    }

    @Test
    public void isEmptyWorksWhenNotEmpty() {
        Book book = new Book("Jotain", "tekija");
        assertEquals(false, book.isEmpty());
    }

    @Test
    public void equalsWorksWhenNotEqual() {
        Book book = new Book("Kirja", "Jaska J");
        Book anotherBook = new Book("Kirja", "Jaska");

        assertEquals(false, book.equals(anotherBook));
    }

    @Test
    public void equalsWorksWhenEqual() {
        Book book = new Book("Kirja", "Jaska J");
        Book anotherBook = new Book("Kirja", "Jaska J");

        assertEquals(true, book.equals(anotherBook));
    }

    @Test
    public void constructorWorksAsExpectedId() {
        Book book = new Book(1, "Kirja", "Jaska J");
        assertEquals("Kirja", book.getTitle());
        assertEquals("Jaska J", book.getAuthor());
    }

    @Test
    public void isEmptyWorksEmptyAuthorId() {
        Book book = new Book(1, "Kirja", "");
        assertEquals(true, book.isEmpty());
    }

    @Test
    public void isEmptyWorksEmptyTitleId() {
        Book book = new Book(1, "", "Jaska J");
        assertEquals(true, book.isEmpty());
    }

    @Test
    public void isEmptyWorksBothEmptyId() {
        Book book = new Book(1, "", "");
        assertEquals(true, book.isEmpty());
    }

    @Test
    public void isEmptyWorksWhenNotEmptyId() {
        Book book = new Book(1, "Jotain", "tekija");
        assertEquals(false, book.isEmpty());
    }

    @Test
    public void equalsWorksWhenNotEqualId() {
        Book book = new Book(1, "Kirja", "Jaska J");
        Book anotherBook = new Book(2, "Kirja", "Jaska");

        assertEquals(false, book.equals(anotherBook));
    }

    @Test
    public void equalsWorksWhenEqualId() {
        Book book = new Book(1, "Kirja", "Jaska J");
        Book anotherBook = new Book(2, "Kirja", "Jaska J");

        assertEquals(true, book.equals(anotherBook));
    }

    @Test
    public void comparingWorksAuthorFirst() {
        Book first = new Book(1, "Kirja", "Aatami");
        Book second = new Book(2, "Kirja", "Bert");

        assertEquals(-1, first.compareTo(second));
    }

    @Test
    public void equalsCaseSensitiveWorks() {
        Book first = new Book("Kirja", "Aatami");
        Book second = new Book("kirja", "Aatami");
        Book third = new Book("kirja", "Aatami");

        assertFalse(first.equalsCaseSensitive(second));
        assertTrue(third.equalsCaseSensitive(second));
    }
}
