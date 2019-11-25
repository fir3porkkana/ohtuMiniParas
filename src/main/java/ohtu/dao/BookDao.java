package ohtu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ohtu.interfaces.*;
import ohtu.objects.Book;

public class BookDao implements Dao<Book, String> {

  String url;

  public BookDao() {
    this.url = "jdbc:sqlite:./books.db";
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

  @Override
  public void create(Book book) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:sqlite:./books.db");

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
  public Book update(Book object) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void delete(Book book) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:sqlite:./books.db");

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
    Connection connection = DriverManager.getConnection("jdbc:sqlite:./books.db");
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