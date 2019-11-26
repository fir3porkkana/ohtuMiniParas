package ohtu;

import javafx.stage.Stage;
import javafx.application.Application;

import ohtu.app.UI;
import ohtu.dao.BookDao;
import ohtu.objects.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        BookDao bookDao = new BookDao("books.db");
        Bookmarks bookmarks = new Bookmarks(bookDao);
        bookmarks.init();

        UI ui = new UI(bookmarks);
        ui.start(stage);
    }

    public static void main(String[] args) {

        launch(Main.class);

    }

}