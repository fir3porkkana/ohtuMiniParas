package ohtu.objects;

import java.util.ArrayList;
import java.util.List;

import ohtu.dao.*;

public class Bookmarks {
  private List<Book> bookmarks;
  private BookDao bookDao;

  public Bookmarks() {
    this.bookDao = new BookDao();
    this.bookmarks = new ArrayList<>();
  }

  public void init() {

    try {

      bookmarks = bookDao.list();
    } catch (Exception e) {
      System.out.println("Error retrieving books from database: \n" + e);
    }
  }

  public void addBookmark(Book book) {
    try {
      bookDao.create(book);
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