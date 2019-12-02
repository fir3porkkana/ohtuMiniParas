package ohtu.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import ohtu.app.components.BookList;
import ohtu.dao.BookDao;
import ohtu.objects.Bookmarks;
import javafx.application.Application;
import javafx.scene.Scene;

public class UI {

    private Bookmarks bookmarks;

    public UI(Bookmarks bookmarks) {

        this.bookmarks = bookmarks;
        bookmarks.init();
    }

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