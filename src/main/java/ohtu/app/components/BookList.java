package ohtu.app.components;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ohtu.objects.Book;
import ohtu.objects.Bookmarks;
import ohtu.dao.*;

public class BookList extends GridPane {

    private Bookmarks bookmarks;

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
        Button editBook = new Button("Edit book");
        Label bookAuthor = new Label("");
        Label bookTitle = new Label("");
        TextField editAuthorField = new TextField();
        editAuthorField.setPromptText("Set new Author");
        TextField editTitleField = new TextField();
        editTitleField.setPromptText("Set new Title");
        selectedBookDisplay.add(new Label("Author"), 0, 0);
        selectedBookDisplay.add(new Label("Title"), 0, 1);
        selectedBookDisplay.add(bookAuthor, 1, 0);
        selectedBookDisplay.add(bookTitle, 1, 1);
        selectedBookDisplay.add(deleteBook, 0,2);
        selectedBookDisplay.add(editAuthorField, 0, 3);
        selectedBookDisplay.add(editTitleField, 1, 3);
        selectedBookDisplay.add(editBook, 0, 4);
        

        selectedBookDisplay.setPadding(new Insets(10, 10, 10, 10));

        // Setting size for the pane
        this.setMinSize(400, 200);

        // Setting the padding
        this.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        ListView<Book> bookList = new ListView<Book>();
        bookList.setId("bookList");
        //bookList.setSpacing(10);
        
        //ScrollPane scrollableBookList = new ScrollPane(bookList);
        //scrollableBookList.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //scrollableBookList.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //scrollableBookList.setPrefSize(300, 2000);

        // Arranging all the nodes in the grid
        this.add(titleLabel, 0, 0);
        this.add(titleInput, 1, 0);
        this.add(authorLabel, 0, 1);
        this.add(authorInput, 1, 1);
        this.add(submitButton, 0, 2);
        this.add(bookList, 0, 3);
        this.add(selectedBookDisplay, 1, 3);

        submitButton.setOnAction((event) -> {
            Book book = new Book(titleInput.getText(), authorInput.getText());
            if (checkBook(book)) {
                refreshBookmarks(bookList, bookAuthor, bookTitle);
                authorInput.setText("");
                titleInput.setText("");
            } else {
                showNewAlert("Book exists","The database already contains this book");
            }
        });

        deleteBook.setOnAction(e->{
            Book selectedBook = bookList.getSelectionModel().getSelectedItem();
            if(selectedBook == null) {
                showNewAlert("Not selected","No book has been selected");
            } else {
                if(deleteBook(selectedBook)){
                    selectedBook = null;
                    refreshBookmarks(bookList, bookAuthor, bookTitle);
                    bookAuthor.setText("");
                    bookTitle.setText("");
                }
            }
        });
        
        editBook.setOnAction(e -> {
            Book selectedBook = bookList.getSelectionModel().getSelectedItem();

            if (selectedBook == null) {
                showNewAlert("Not selected","No book has been selected");
            } else {
                editBook(selectedBook, new Book(editTitleField.getText(), editAuthorField.getText()));
                refreshBookmarks(bookList, bookAuthor, bookTitle);
                bookAuthor.setText(editAuthorField.getText());
                bookTitle.setText(editTitleField.getText());
            }
        });

        bookList.setOnMouseClicked(e->{
            Book selectedBook = bookList.getSelectionModel().getSelectedItem();
            if(selectedBook == null) return;

            bookAuthor.setText(selectedBook.getAuthor());
            bookTitle.setText(selectedBook.getTitle());
        });

        refreshBookmarks(bookList, bookAuthor, bookTitle);
    }

    private void showNewAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void refreshBookmarks(ListView<Book> bookList, Label bookAuthor, Label bookTitle){
        bookList.getItems().clear();

        bookmarks.getBookmarks().forEach(book -> {
            bookList.getItems().add(book);
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
    
    private void editBook(Book book, Book updatedBook) {
        bookmarks.updateBookmark(book, updatedBook);
    }

    public Bookmarks getBookmarks() {
        return bookmarks;
    }
}