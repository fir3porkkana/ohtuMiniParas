package ohtu.app;

import javafx.stage.Stage;
import ohtu.app.components.BookList;
import ohtu.objects.Bookmarks;
import javafx.scene.Scene;

public class UI {

    private Bookmarks bookmarks;

    public UI(Bookmarks bookmarks) {
        this.bookmarks = bookmarks;
    }

    public void start(Stage stage) {

        BookList gridPane = new BookList(bookmarks);

        Scene bookList = new Scene(gridPane);

        stage.setScene(bookList);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }
}