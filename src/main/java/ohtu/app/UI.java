package ohtu.app;

import javafx.stage.Stage;
import ohtu.app.components.BookList;
import javafx.scene.Scene;

public class UI {

    public void start(Stage stage) {

        BookList gridPane = new BookList();

        Scene bookList = new Scene(gridPane);

        stage.setScene(bookList);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }
}