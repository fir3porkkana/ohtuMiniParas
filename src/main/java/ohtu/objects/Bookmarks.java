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
      bookmarks.add(book);
    } catch (Exception e) {
      System.out.println("Error adding book to database: " + e);
    }

  }

  public boolean contains(Book book) {
    return bookmarks.contains(book);
  }

  public void removeBookmark(Book book) {
    try {
      dao.delete(book);
      bookmarks.remove(book);
    } catch (Exception e) {
      System.out.println("Error removing book from database: " + e);
    }

  }

  public void updateBookmark(Book book, Book updatedBook) {
    try {
      dao.update(book, updatedBook);
      int index = bookmarks.indexOf(book);
      bookmarks.set(index, updatedBook);
    } catch (Exception e) {
      System.out.println("Error updating book: " + e);
    }

  }

  /**
   * @return the bookmarks
   */
  public List<Book> getBookmarks() {
    return bookmarks;
  }
}