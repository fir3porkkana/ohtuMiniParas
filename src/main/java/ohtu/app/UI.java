package ohtu.app;

import javafx.stage.Stage;
import ohtu.app.components.BookList;
import ohtu.dao.BookDao;
import ohtu.objects.Bookmarks;
import javafx.application.Application;
import javafx.scene.Scene;

public class UI extends Application {

    private Bookmarks bookmarks;
    private BookDao bookDao;

    public UI() {

        this.bookDao = new BookDao("books.db");
        this.bookmarks = new Bookmarks(bookDao);
        bookmarks.init();
    }

    public static void main(String[] args) {
        Application.launch(args);

    }

    @Override
    public void start(Stage stage) {

        FileSelector fileSelector = new FileSelector(stage);

        BookList gridPane = new BookList(bookmarks, fileSelector);

        Scene bookList = new Scene(gridPane);

        stage.setScene(bookList);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }
}