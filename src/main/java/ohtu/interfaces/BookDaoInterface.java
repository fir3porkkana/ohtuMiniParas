package ohtu.interfaces;

import java.util.ArrayList;
import ohtu.objects.Book;

public interface BookDaoInterface {
  public void addBook(Book book);

  public ArrayList<Book> listBooks();
}