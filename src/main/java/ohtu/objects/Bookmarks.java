package ohtu.objects;

import java.util.ArrayList;
import java.util.List;

import ohtu.dao.*;
import ohtu.interfaces.*;

public class Bookmarks {
  private List<Book> bookmarks;
  private Dao dao;

  public Bookmarks(Dao dao) {
    this.dao = dao;
    this.bookmarks = new ArrayList<>();
  }

  public void init() {

    try {

      bookmarks = dao.list();
    } catch (Exception e) {
      System.out.println("Error retrieving books from database: \n" + e);
    }
  }

  public void addBookmark(Book book) {
    try {
      dao.create(book);
    } catch (Exception e) {
      System.out.println("Error adding book to database: " + e);
    }
    bookmarks.add(book);
  }

  public void removeBookmark(Book book) {
    bookmarks.remove(book);
  }

  /**
   * @return the bookmarks
   */
  public List<Book> getBookmarks() {
    return bookmarks;
  }
}