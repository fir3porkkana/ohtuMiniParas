package ohtu.app.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ohtu.objects.Book;
import ohtu.objects.Bookmarks;

public class BookList extends GridPane {

    private Bookmarks bookmarks;

    public BookList() {

        bookmarks = new Bookmarks();
        bookmarks.init();

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
        // Setting size for the pane
        this.setMinSize(400, 200);

        // Setting the padding
        this.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        VBox bookList = new VBox();
        bookList.setId("bookList");
        bookList.setSpacing(10);

        // Arranging all the nodes in the grid
        this.add(titleLabel, 0, 0);
        this.add(titleInput, 1, 0);
        this.add(authorLabel, 0, 1);
        this.add(authorInput, 1, 1);
        this.add(submitButton, 0, 2);
        this.add(bookList, 0, 3);

        submitButton.setOnAction((event) -> {
            Book book = new Book(titleInput.getText(), authorInput.getText());
            bookmarks.addBookmark(book);

            bookList.getChildren().add(new Label(book.toString()));
            authorInput.setText("");
            titleInput.setText("");
        });

        bookmarks.getBookmarks().forEach(book -> {
            Label bookLabel = new Label(book.toString());

            bookList.getChildren().add(bookLabel);
        });
    }

    public Bookmarks getBookmarks() {
        return bookmarks;
    }
}