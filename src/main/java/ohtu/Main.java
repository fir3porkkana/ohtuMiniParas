package ohtu;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ohtu.dao.BookDao;
import ohtu.objects.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        BookDao bookDao = new BookDao();

        try {
            bookDao.createNewTable();

        } catch (Exception e) {
            System.out.println("error creating table: " + e);
        }

        Bookmarks bookmarks = new Bookmarks();
        bookmarks.addBookmark(new Book("Violence", "Slavoj Žižek"));
        bookmarks.addBookmark(new Book("Of mice and men", "John Steinbeck"));

        Label authorLabel = new Label("Author");
        Label titleLabel = new Label("Title");

        TextField authorInput = new TextField();
        authorInput.setId("author_input");
        authorInput.setPromptText("Author");

        TextField titleInput = new TextField();
        titleInput.setId("title_input");
        titleInput.setPromptText("Title");

        Button submitButton = new Button("Add book");
        submitButton.setId("submit_button");

        GridPane gridPane = new GridPane();

        // Setting size for the pane
        gridPane.setMinSize(400, 200);

        // Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        VBox bookList = new VBox();
        bookList.setId("bookList");
        bookList.setSpacing(10);

        try {
            bookDao.list().forEach(book -> {
                Label bookLabel = new Label(book.toString());
                bookList.getChildren().add(bookLabel);
            });
        } catch (Exception e) {

        }
        bookmarks.getBookmarks().forEach(book -> {
            Label bookLabel = new Label(book.toString());
            bookList.getChildren().add(bookLabel);
        });

        // Arranging all the nodes in the grid
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleInput, 1, 0);
        gridPane.add(authorLabel, 0, 1);
        gridPane.add(authorInput, 1, 1);
        gridPane.add(submitButton, 0, 2);
        gridPane.add(bookList, 0, 3);

        submitButton.setOnAction((event) -> {
            Book book = new Book(titleInput.getText(), authorInput.getText());
            bookmarks.addBookmark(book);

            bookList.getChildren().add(new Label(book.toString()));

            try {
                bookDao.create(book);

            } catch (Exception e) {
                System.out.println("adding book: " + e);
            }
            authorInput.setText("");
            titleInput.setText("");
        });

        Scene scene = new Scene(gridPane);

        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }

    public static void main(String[] args) {

        launch(Main.class);

        // bookmarks.getBookmarks().forEach(b -> System.out.println(b.toString()));
    }

}