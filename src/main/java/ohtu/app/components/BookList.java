package ohtu.app.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ohtu.objects.Book;
import ohtu.objects.Bookmarks;
import ohtu.dao.*;

public class BookList extends GridPane {

    private Bookmarks bookmarks;
    private Book selectedBook = null;

    public BookList() {

        bookmarks = new Bookmarks(new BookDao());
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

        //Display for selected book
        GridPane selectedBookDisplay = new GridPane();
        Button deleteBook = new Button("Delete book");
        Label bookAuthor = new Label("");
        Label bookTitle = new Label("");
        selectedBookDisplay.add(new Label("Author"), 0, 0);
        selectedBookDisplay.add(new Label("Title"), 0, 1);
        selectedBookDisplay.add(bookAuthor, 1, 0);
        selectedBookDisplay.add(bookTitle, 1, 1);
        selectedBookDisplay.add(deleteBook, 0,2);

        selectedBookDisplay.setPadding(new Insets(10, 10, 10, 10));

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
        
        ScrollPane scrollableBookList = new ScrollPane(bookList);
        scrollableBookList.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollableBookList.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollableBookList.setPrefSize(300, 2000);

        // Arranging all the nodes in the grid
        this.add(titleLabel, 0, 0);
        this.add(titleInput, 1, 0);
        this.add(authorLabel, 0, 1);
        this.add(authorInput, 1, 1);
        this.add(submitButton, 0, 2);
        this.add(scrollableBookList, 0, 3);
        this.add(selectedBookDisplay, 1, 3);

        submitButton.setOnAction((event) -> {
            Book book = new Book(titleInput.getText(), authorInput.getText());
            if (checkBook(book)) {
                refreshBookmarks(bookList, bookAuthor, bookTitle);
                //bookList.getChildren().add(new Label(book.toString()));
                authorInput.setText("");
                titleInput.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Book exists");
                alert.setHeaderText(null);
                alert.setContentText("The database already contains this book");
                alert.showAndWait();
            }
        });

        deleteBook.setOnAction(e->{
            if(selectedBook == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not selected");
                alert.setHeaderText(null);
                alert.setContentText("No book has been selected");
                alert.showAndWait();
                return;
            };

            if(deleteBook(selectedBook)){
                selectedBook = null;
                refreshBookmarks(bookList, bookAuthor, bookTitle);
                bookAuthor.setText("");
                bookTitle.setText("");
            }
        });

        refreshBookmarks(bookList, bookAuthor, bookTitle);
    }

    private void refreshBookmarks(VBox bookList, Label bookAuthor, Label bookTitle){
        bookList.getChildren().removeAll(bookList.getChildren());

        bookmarks.getBookmarks().forEach(book -> {
            Label bookLabel = new Label(book.toString());
            bookLabel.setOnMouseClicked(e->{
                selectedBook = book;
                bookAuthor.setText(book.getAuthor());
                bookTitle.setText(book.getTitle());
            });

            bookList.getChildren().add(bookLabel);
        });
    }
    
    public Boolean checkBook(Book book) {
        for (Book existingBook : bookmarks.getBookmarks()) {
            if (existingBook.equals(book)){
                return false;
            }
        }
        bookmarks.addBookmark(book);
        return true;
    }

    private Boolean deleteBook(Book book){
        if(!bookmarks.getBookmarks().contains(book)) return false;

        bookmarks.removeBookmark(book);
        return true;
    }

    public Bookmarks getBookmarks() {
        return bookmarks;
    }
}