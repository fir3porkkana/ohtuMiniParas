package ohtu;

import javafx.application.Application;
import javafx.stage.Stage;
import ohtu.app.UI;
import ohtu.dao.BookDao;
import ohtu.objects.Bookmarks;

public class GUIStarter extends Application {

  Bookmarks bookmarks;
  BookDao bookDao;

  public GUIStarter() {

    this.bookDao = new BookDao("books.db");
    this.bookmarks = new Bookmarks(bookDao);

  }

  @Override
  public void start(Stage stage) throws Exception {
    UI ui = new UI(bookmarks);

    ui.start(stage);
  }
}