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

  public AudiobookTest() {

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
  public void addingTimestampsWorks() {
    Audiobook book = new Audiobook("Kirja", "Jaska J", new File(""));

    assertTrue(book.getTimestampList().isEmpty());

    book.addTimestamp(new Timestamp(""));
    assertEquals(1, book.getTimestampList().size());
  }

}
