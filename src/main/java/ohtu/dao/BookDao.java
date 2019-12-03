package ohtu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import ohtu.interfaces.*;
import ohtu.objects.*;
import ohtu.objects.Timestamp;

public class BookDao implements Dao<BookSuper, String> {

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
    // SQLite connection stringfile:///home/stuomela/Documents/ohtuMiniParas/build/reports/tests/test/index.html

    // SQL statement for creating a new table
    String sql = "CREATE TABLE IF NOT EXISTS BOOKS "
        + "( title text NOT NULL,  author text NOT NULL, PRIMARY KEY (title, author) );";

    Connection conn = DriverManager.getConnection(url);
    PreparedStatement stmt = conn.prepareStatement(sql);
    // create a new table
    stmt.execute();
    stmt.close();
    
    sql = "CREATE TABLE IF NOT EXISTS AUDIOBOOKS"
            + "(title text NOT NULL, author text NOT NULL, url text NOT NULL, PRIMARY KEY (title, author));";
    conn = DriverManager.getConnection(url);
    stmt = conn.prepareStatement(sql);
    // create a new table
    stmt.execute();
    stmt.close();

    sql = "CREATE TABLE IF NOT EXISTS Timestamps" +
            "(" +
                "id integer PRIMARY KEY," +
                "stamp text NOT NULL," +
                "title text NOT NULL," +
                "author text NOT NULL," +
                "FOREIGN KEY (title, author) REFERENCES AUDIOBOOKS (title, author)" +
            ");";
      conn = DriverManager.getConnection(url);
      stmt = conn.prepareStatement(sql);
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
  public void create(BookSuper book) throws SQLException {
    if (book instanceof Book) {
        Connection connection = DriverManager.getConnection(url);

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Books (title, author) VALUES (?, ?)");
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    if (book instanceof Audiobook) {
        Audiobook audiobook = (Audiobook) book;
        Connection connection = DriverManager.getConnection(url);

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO AUDIOBOOKS (title, author, url) VALUES (?, ?, ?)");
        stmt.setString(1, audiobook.getTitle());
        stmt.setString(2, audiobook.getAuthor());
        stmt.setString(3, audiobook.getMp3().toString());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
  }

  public void addTimestamp(Audiobook book, Timestamp timestamp) throws SQLException{
      Connection connection = DriverManager.getConnection(url);

      PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timestamps (stamp, title, author) VALUES (?, ?, ?)");
      stmt.setString(1, timestamp.getTimestampString());
      stmt.setString(2, book.getTitle());
      stmt.setString(3, book.getAuthor());

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
  public void update(BookSuper book, BookSuper updatedBook) throws SQLException {
    if (book instanceof Book && book instanceof Book) {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement stmt = connection
        .prepareStatement("UPDATE Books SET title = ? , author = ? WHERE title = ? AND author = ?");
        stmt.setString(1, updatedBook.getTitle());
        stmt.setString(2, updatedBook.getAuthor());
        stmt.setString(3, book.getTitle());
        stmt.setString(4, book.getAuthor());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    if (book instanceof Audiobook && updatedBook instanceof Audiobook) {
        Connection connection = DriverManager.getConnection(url);
        Audiobook updateBook = (Audiobook) updatedBook;
        PreparedStatement stmt = connection
        .prepareStatement("UPDATE Audiobooks SET title = ? , author = ? WHERE title = ? AND author = ?");
        stmt.setString(1, updateBook.getTitle());
        stmt.setString(2, updateBook.getAuthor());
        stmt.setString(3, book.getTitle());
        stmt.setString(4, book.getAuthor());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    

  }

  @Override
  public void delete(BookSuper book) throws SQLException {
    if (book instanceof Book) {
        Connection connection = DriverManager.getConnection(url);

        // Remove book from database, based on combination of fields title and author.
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Books WHERE title = ? AND author= ?");
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    if (book instanceof Audiobook) {
        Connection connection = DriverManager.getConnection(url);

        // Remove book from database, based on combination of fields title and author.
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Audiobooks WHERE title = ? AND author= ?");
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
  }

  @Override
  public List<BookSuper> list() throws SQLException {
    List<BookSuper> list = new ArrayList<>();
    Connection connection = DriverManager.getConnection(url);
    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Books");
    ResultSet resultSet = stmt.executeQuery();
    while (resultSet.next()) {
      String title = resultSet.getString("title");
      String author = resultSet.getString("author");
      Book book = new Book(title, author);
      list.add(book);
    }
    
    stmt = connection.prepareStatement("SELECT * FROM AUDIOBOOKS");
    resultSet = stmt.executeQuery();
    while (resultSet.next()) {
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        String url = resultSet.getString("url");
        
        Audiobook book = new Audiobook(title, author, new File(url));

        book.addTimestamps(getTimestamps(connection, book));

        list.add(book);
    }

    stmt.close();
    connection.close();
    return list;
  }

  private List<Timestamp> getTimestamps(Connection connection, Audiobook audiobook) throws SQLException{
      List<Timestamp> list = new ArrayList<>();

      PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timestamps WHERE title = ? AND author= ?");
      stmt.setString(1, audiobook.getTitle());
      stmt.setString(2, audiobook.getAuthor());

      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
          String TimeString = resultSet.getString("stamp");

          Timestamp timestamp = new Timestamp(TimeString);
          list.add(timestamp);
      }

      stmt.close();
      return list;
  }


}