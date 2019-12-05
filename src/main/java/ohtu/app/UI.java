package ohtu.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import ohtu.app.components.BookList;
import ohtu.dao.BookDao;
import ohtu.objects.Bookmarks;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser.ExtensionFilter;

public class UI {

    private Bookmarks bookmarks;

    public UI(Bookmarks bookmarks) {

        this.bookmarks = bookmarks;
        bookmarks.init();
    }

    public void start(Stage stage) {

        FileSelector audioSelector = new FileSelector(stage);
        audioSelector.setTitle("Choose audio file");
        audioSelector.addFilter(new ExtensionFilter("Audio Files", "*.mp3"));

        // FileSelector photoSelector = new FileSelector(stage);
        // photoSelector.setTitle("Choose book cover");
        // photoSelector.addFilter(new ExtensionFilter("Picture files", "*.jpg",
        // "*.png"));

        BookList gridPane = new BookList(bookmarks, audioSelector);

        Scene bookList = new Scene(gridPane);

        stage.setScene(bookList);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }
}