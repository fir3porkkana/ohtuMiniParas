package ohtu.objects;

import java.util.HashSet;;

public class Bookmarks {
  private HashSet<Book> bookmarks;

  public Bookmarks() {
    this.bookmarks = new HashSet<>();
  }

  public void addBookmark(Book book) {
    bookmarks.add(book);
  }

  public void removeBookmark(Book book) {
    bookmarks.remove(book);
  }

  /**
   * @return the bookmarks
   */
  public HashSet<Book> getBookmarks() {
    return bookmarks;
  }
}