package ohtu.app.components;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ohtu.objects.Book;
import ohtu.objects.Bookmarks;
import ohtu.dao.*;


public class BookList extends GridPane {

    private Bookmarks bookmarks;

    private TextField authorInput = new TextField();
    private TextField titleInput = new TextField();

    private ListView<Book> bookListView = new ListView<>();

    private Label bookInfoAuthor = new Label("");
    private Label bookInfoTitle = new Label("");

    private TextField editAuthorField = new TextField();
    private TextField editTitleField = new TextField();

    public BookList() {

        bookmarks = new Bookmarks(new BookDao());
        bookmarks.init();

        Label authorLabel = new Label("Author");
        authorInput.setId("author_input");
        authorInput.setPromptText("Author");

        Label titleLabel = new Label("Title");
        titleInput.setId("title_input");
        titleInput.setPromptText("Title");

        Button addBookButton = new Button("Add book");
        addBookButton.setId("submit_button");

        //Display for selected book
        GridPane selectedBookDisplay = new GridPane();
        selectedBookDisplay.setPadding(new Insets(10, 10, 10, 10));

        Button deleteBookButton = new Button("Delete book");
        Button editBookButton = new Button("Edit book");

        editAuthorField.setPromptText("Set new Author");
        editTitleField.setPromptText("Set new Title");

        selectedBookDisplay.add(new Label("Author"), 0, 0);
        selectedBookDisplay.add(new Label("Title"), 0, 1);
        selectedBookDisplay.add(bookInfoAuthor, 1, 0);
        selectedBookDisplay.add(bookInfoTitle, 1, 1);
        selectedBookDisplay.add(deleteBookButton, 0,2);
        selectedBookDisplay.add(editAuthorField, 0, 3);
        selectedBookDisplay.add(editTitleField, 1, 3);
        selectedBookDisplay.add(editBookButton, 0, 4);

        // Setting size for the pane
        this.setMinSize(400, 200);

        // Setting the padding
        this.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        bookListView.setId("bookList");

        // Arranging all the nodes in the grid
        this.add(titleLabel, 0, 0);
        this.add(titleInput, 1, 0);
        this.add(authorLabel, 0, 1);
        this.add(authorInput, 1, 1);
        this.add(addBookButton, 0, 2);
        this.add(bookListView, 0, 3);
        this.add(selectedBookDisplay, 1, 3);

        addBookButton.setOnAction(this::addBookAction);
        deleteBookButton.setOnAction(this::deleteBookAction);
        editBookButton.setOnAction(this::editBookAction);

        bookListView.setOnMouseClicked(this::bookSelectedAction);

        refreshBookmarks();
    }

    private void bookSelectedAction(javafx.scene.input.MouseEvent e){
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if(selectedBook == null) return;
        setBookInfoText(selectedBook.getAuthor(), selectedBook.getTitle());
    }

    private void addBookAction(ActionEvent e){
        Book book = new Book(titleInput.getText(), authorInput.getText());
        if (checkBook(book)) {
            refreshBookmarks();
            setBookInputText("","");
        } else {
            showNewAlert("Book exists","The database already contains this book");
        }
    }

    private void editBookAction(ActionEvent e){
        Book selectedBook = getSelectedBook();

        if (selectedBook == null) {
            showNewAlert("Not selected","No book has been selected");
        } else {
            editBook(selectedBook, new Book(editTitleField.getText(), editAuthorField.getText()));
            refreshBookmarks();
            setBookInfoText(editTitleField.getText(), editAuthorField.getText());
        }
    }

    private void deleteBookAction(ActionEvent e){
        Book selectedBook = getSelectedBook();
        if(selectedBook == null) {
            showNewAlert("Not selected","No book has been selected");
        } else if (deleteBook(selectedBook)) {
            refreshBookmarks();
            setBookInfoText("", "");
        }
    }

    private void setBookInputText(String author, String title){
        authorInput.setText(author);
        titleInput.setText(title);
    }

    private void setBookInfoText(String author, String title){
        bookInfoAuthor.setText(author);
        bookInfoTitle.setText(title);
    }

    private Book getSelectedBook(){
        return bookListView.getSelectionModel().getSelectedItem();
    }

    private void showNewAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void refreshBookmarks(){
        bookListView.getItems().clear();
        bookListView.getItems().addAll(bookmarks.getBookmarks());
    }
    
    public Boolean checkBook(Book book) {
        if(bookmarks.getBookmarks().contains(book))
            return false;
        bookmarks.addBookmark(book);
        return true;
    }

    private Boolean deleteBook(Book book){
        if(!bookmarks.getBookmarks().contains(book))
            return false;
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