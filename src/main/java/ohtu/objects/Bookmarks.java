package ohtu.objects;

import java.util.ArrayList;
import java.util.List;

import ohtu.dao.*;
import ohtu.interfaces.*;

public class Bookmarks {
  private List<BookSuper> bookmarks;
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

  public void addBookmark(BookSuper book) {
    if (book instanceof Book) {
      book = (Book) book;
      try {
        dao.create(book);
        bookmarks.add(book);
      } catch (Exception e) {
        System.out.println("Error adding book to database: " + e);
      }
    }
  }

  public boolean contains(BookSuper book) {
    if (book instanceof Book) {
      book = (Book) book;
    }
    return bookmarks.contains(book);
  }

  public void removeBookmark(BookSuper book) {

    if (book instanceof Book) {
      book = (Book) book;
      try {
        dao.delete(book);
        bookmarks.remove(book);
      } catch (Exception e) {
        System.out.println("Error removing book from database: " + e);
      }
    }

  }

  public void updateBookmark(BookSuper book, BookSuper updatedBook) {
    if (book instanceof Book) {
      book = (Book) book;
      try {
        dao.update(book, updatedBook);
        int index = bookmarks.indexOf(book);
        bookmarks.set(index, updatedBook);
      } catch (Exception e) {
        System.out.println("Error updating book: " + e);
      }
    }

  }

  public void emptyBookmarks() {
    init();
  }

  /**
   * @return the bookmarks
   */
  public List<BookSuper> getBookmarks() {
    return bookmarks;
  }
}