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

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class AudiobookTest {

  private File testFile;

  public AudiobookTest() {

  }

  @Before
  public void init() {
    this.testFile = new File("yuribajstestfilexd");
  }

  @Test
  public void constructorWorksAsExpected() {
    Audiobook book = new Audiobook("Kirja", "Jaska J", new File(""));
    assertEquals("Kirja", book.getTitle());
    assertEquals("Jaska J", book.getAuthor());
  }

  @Test
  public void constructorWorksAsExpectedWithTimestamps() {

    Audiobook book = new Audiobook("Kirja", "Jaska J", new File(""), new ArrayList<Timestamp>(), new File(""));
    assertEquals("Kirja", book.getTitle());
    assertEquals("Jaska J", book.getAuthor());
    assertEquals(new ArrayList<Timestamp>(), book.getTimestampList());
  }

  @Test
  public void isEmptyWorksWithEmptyAuthor() {
    Audiobook audiobook = new Audiobook("Kirja", "", testFile);
    assertEquals(true, audiobook.isEmpty());
  }

  @Test
  public void isEmptyWorksWithEmptyTitle() {
    Audiobook audiobook = new Audiobook("", "Joosu J", testFile);
    assertEquals(true, audiobook.isEmpty());
  }

  @Test
  public void isEmptyWorksWithEmptyFile() {
    Audiobook audiobook = new Audiobook("Kirja", "Joosu J", null);
    assertEquals(true, audiobook.isEmpty());
  }

  @Test
  public void isEmptyWorksWhenNotEmpty() {
    Audiobook audiobook = new Audiobook("Kirja", "Joosu J", testFile);
    assertFalse(audiobook.isEmpty());
  }

  @Test
  public void equalsWorksWhenNotEqual() {
    Audiobook book = new Audiobook("Kirja", "Jaska J", testFile);
    Audiobook anotherBook = new Audiobook("Kirja", "Jaska", testFile);

    assertFalse(book.equals(anotherBook));
  }

  @Test
  public void equalsWorksWhenEqual() {
    Audiobook book = new Audiobook("Kirja", "Joosu J", testFile);
    Audiobook anotherBook = new Audiobook("Kirja", "Joosu J", testFile);

    assertTrue(book.equals(anotherBook));
  }

  @Test
  public void constructorWorksAsExpectedId() {
    Audiobook book = new Audiobook("Kirja", "Jaska J", testFile);
    assertEquals("Kirja", book.getTitle());
    assertEquals("Jaska J", book.getAuthor());
  }

  @Test
  public void addingTimestampsWorks() {
    Audiobook book = new Audiobook("Kirja", "Jaska J", new File(""));

    assertTrue(book.getTimestampList().isEmpty());

    book.addTimestamp(new Timestamp(""));
    assertEquals(1, book.getTimestampList().size());
  }

}
