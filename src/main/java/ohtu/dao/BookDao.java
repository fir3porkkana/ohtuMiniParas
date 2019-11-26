package ohtu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ohtu.interfaces.*;
import ohtu.objects.Book;

public class BookDao implements Dao<Book, String> {

  private String url;

  public BookDao(String DBFileName) {
    this.url = "jdbc:sqlite:./" + DBFileName;
    try {
      createNewTable();
    } catch (Exception e) {
      System.out.println("Error creating or connecting to database: \n" + e);
    }
  }

  public void createNewTable() throws SQLException {
    // SQLite connection string

    // SQL statement for creating a new table
    String sql = "CREATE TABLE IF NOT EXISTS BOOKS "
        + "( id integer PRIMARY KEY, title text NOT NULL,  author text NOT NULL );";

    Connection conn = DriverManager.getConnection(url);
    PreparedStatement stmt = conn.prepareStatement(sql);
    // create a new table
    stmt.execute();
    stmt.close();
    conn.close();

  }

  public void emptyTable() throws SQLException {
    String sql = "DELETE FROM books";

    Connection conn = DriverManager.getConnection(url);
    PreparedStatement stmt = conn.prepareStatement(sql);
    // create a new table
    stmt.executeUpdate();
    stmt.close();
    conn.close();
  }

  @Override
  public void create(Book book) throws SQLException {
    Connection connection = DriverManager.getConnection(url);

    PreparedStatement stmt = connection.prepareStatement("INSERT INTO Books (title, author) VALUES (?, ?)");
    stmt.setString(1, book.getTitle());
    stmt.setString(2, book.getAuthor());

    stmt.executeUpdate();
    stmt.close();
    connection.close();
  }

  @Override
  public Book read(String key) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(Book book, Book updatedBook) throws SQLException {
    Connection connection = DriverManager.getConnection(url);
    PreparedStatement stmt = connection
        .prepareStatement("UPDATE Books SET title = ? , author = ? WHERE title = ? AND author = ?");
    stmt.setString(1, updatedBook.getTitle());
    stmt.setString(2, updatedBook.getTitle());
    stmt.setString(3, book.getTitle());
    stmt.setString(4, book.getAuthor());

    stmt.executeUpdate();
    stmt.close();
    connection.close();

  }

  @Override
  public void delete(Book book) throws SQLException {
    Connection connection = DriverManager.getConnection(url);

    // Remove book from database, based on combination of fields title and author.
    PreparedStatement stmt = connection.prepareStatement("DELETE FROM Books WHERE title = ? AND author= ?");
    stmt.setString(1, book.getTitle());
    stmt.setString(2, book.getAuthor());

    stmt.executeUpdate();
    stmt.close();
    connection.close();
  }

  @Override
  public List<Book> list() throws SQLException {
    List<Book> list = new ArrayList<>();
    Connection connection = DriverManager.getConnection(url);
    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Books");
    ResultSet resultSet = stmt.executeQuery();
    while (resultSet.next()) {
      String title = resultSet.getString("title");
      String author = resultSet.getString("author");
      Book book = new Book(title, author);
      list.add(book);
    }
    return list;
  }

}