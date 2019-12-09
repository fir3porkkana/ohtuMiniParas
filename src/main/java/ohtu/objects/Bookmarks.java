package ohtu.objects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;

import ohtu.dao.*;
import ohtu.interfaces.*;

public class Bookmarks {
  private List<BookSuper> bookmarks;
  private Iterator<BookSuper> iterator;
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
    if (book instanceof Audiobook) {
      book = (Audiobook) book;
      try {
        dao.create(book);
        bookmarks.add(book);
      } catch (Exception e) {
        System.out.println("Error adding book to database: " + e);
      }
      return;
    }
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

  public void addTimestamp(Audiobook audiobook, Timestamp timestamp) {
    BookDao bookDao = (BookDao) dao;
    try {
      bookDao.addTimestamp(audiobook, timestamp);
    } catch (SQLException e) {
      System.out.println("Error adding timestamp to database " + e.getMessage());
    }
  }

  public boolean contains(BookSuper book) {
    if (book instanceof Book) {
      Book book1 = (Book) book;
      return containsBook(book1);
    }
    if (book instanceof Audiobook) {
      Audiobook audiobook = (Audiobook) book;
      return containsAudioBook(audiobook);
    }
    return false;
  }

  public boolean containsBook(Book book) {
    for (BookSuper normalBook : bookmarks) {
      if (normalBook instanceof Book) {
        if (normalBook.equals(book)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean containsAudioBook(Audiobook book) {
    for (BookSuper audiobook : bookmarks) {
      if (audiobook instanceof Audiobook) {
        if (audiobook.equals(book)) {
          return true;
        }
      }
    }
    return false;
  }

  public void removeBookmark(BookSuper book) {
    iterator = bookmarks.iterator();
    if (book instanceof Audiobook) {
      Audiobook audiobook = (Audiobook) book;
      try {
        dao.delete(audiobook);
        while (iterator.hasNext()) {
          BookSuper nextBook = iterator.next();
          if (nextBook instanceof Audiobook) {
            if (book.equals(nextBook)) {
              iterator.remove();
            }
          }
        }
      } catch (Exception e) {
        System.out.println("Error removing book from database: " + e);
      }
      return;
    }

    if (book instanceof Book) {
      book = (Book) book;
      try {
        dao.delete(book);
        while (iterator.hasNext()) {
          BookSuper nextBook = iterator.next();
          if (nextBook instanceof Book) {
            if (book.equals(nextBook)) {
              iterator.remove();
            }
          }
        }
      } catch (Exception e) {
        System.out.println("Error removing book from database: " + e);
      }
    }
  }

  public List<BookSuper> searchBookmarks(String search) {
    List<BookSuper> result = bookmarks.stream()
        .filter(bookSuper -> bookSuper.getAuthor().toLowerCase().contains(search.toLowerCase())
            || bookSuper.getTitle().toLowerCase().contains(search.toLowerCase()))
        .collect(Collectors.toList());
    Collections.sort(result);

    return result;
  }

  public void updateBookmark(BookSuper book, BookSuper updatedBook) {
    if (book instanceof Audiobook) {
      Audiobook audiobook = (Audiobook) book;
      Audiobook updatedAudioBook = (Audiobook) updatedBook;
      try {
        dao.update(audiobook, updatedAudioBook);
        int index = bookmarks.indexOf(book);
        // Update timestamps to the ui
        ((Audiobook) updatedBook).addTimestamps(((Audiobook) book).getTimestampList());
        bookmarks.set(index, updatedBook);
      } catch (Exception e) {
        System.out.println("Error updating book: 2 " + e);
      }
      return;
    }
    if (book instanceof Book) {
      book = (Book) book;
      try {
        dao.update(book, updatedBook);
        int index = bookmarks.indexOf(book);
        bookmarks.set(index, updatedBook);
      } catch (Exception e) {
        System.out.println("Error updating book: 1 " + e);
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