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
        Book book = new Book(3, "Kirja", "Jaska J");
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
}
