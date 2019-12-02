package ohtu.app.components;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ohtu.app.FileSelector;
import ohtu.objects.Audiobook;
import ohtu.objects.Book;
import ohtu.objects.BookSuper;
import ohtu.objects.Bookmarks;

public class BookList extends GridPane {

    private Bookmarks bookmarks;
    private BookSuper deletedBook;

    private TextField authorInput = new TextField();
    private TextField titleInput = new TextField();

    private ListView<BookSuper> bookListView = new ListView<>();

    private TextField editTitleField = new TextField();
    private TextField editAuthorField = new TextField();
    private Label audiobookName = new Label();
    private Button undoDeletionButton;

    private FileSelector fileSelector;
    private MediaPlayer mediaPlayer;

    private Label durationLabel;

    public BookList(Bookmarks bookmarks, FileSelector fileSelector) {

        this.bookmarks = bookmarks;
        this.fileSelector = fileSelector;

        Label authorLabel = new Label("Author");
        authorInput.setId("author_input");
        authorInput.setPromptText("Author");

        Label titleLabel = new Label("Title");
        titleInput.setId("title_input");
        titleInput.setPromptText("Title");

        HBox addButtons = new HBox(10);
        Button addBookButton = new Button("Add book");
        Button addAudiobookButton = new Button("Add audiobook");
        addBookButton.setId("submit_button");
        addButtons.getChildren().addAll(addBookButton, addAudiobookButton);

        // Display for selected book
        GridPane selectedBookDisplay = new GridPane();
        selectedBookDisplay.setPadding(new Insets(10, 10, 10, 10));
        selectedBookDisplay.setVgap(5);
        selectedBookDisplay.setHgap(5);

        Button deleteBookButton = new Button("Delete book");
        deleteBookButton.setId("delete_button");
        Button editBookButton = new Button("Save change");
        editBookButton.setId("edit_button");
        undoDeletionButton = new Button("Undo");
        undoDeletionButton.setId("undo_button");
        undoDeletionButton.setVisible(false);

        editAuthorField.setPromptText("Set new Author");
        editAuthorField.setId("edit_author");
        editTitleField.setPromptText("Set new Title");
        editTitleField.setId("edit_title");

        // Display for audio controls
        HBox audioControls = new HBox(10);
        Button playButton = new Button("▶▮▮");
        Button stopButton = new Button("■");

        playButton.setStyle("-fx-text-alignment: right");
        playButton.setShape(new Circle(20));
        playButton.setPadding(Insets.EMPTY);

        playButton.setMinSize(40, 40);
        playButton.setMaxSize(40, 40);
        playButton.setOnAction(this::mediaPlayerPlayAction);

        stopButton.setShape(new Circle(20));
        stopButton.setMinSize(40, 40);
        stopButton.setMaxSize(40, 40);
        stopButton.setOnAction(this::mediaPlayerStopAction);

        durationLabel = new Label();
        Button saveTimestamp = new Button("Timestamp");

        audioControls.getChildren().addAll(playButton, stopButton, saveTimestamp, durationLabel);

        selectedBookDisplay.add(new Label("Title"), 0, 0);
        selectedBookDisplay.add(new Label("Author"), 0, 1);
        selectedBookDisplay.add(editTitleField, 1, 0);
        selectedBookDisplay.add(editAuthorField, 1, 1);
        selectedBookDisplay.add(audiobookName, 1,2);
        selectedBookDisplay.add(deleteBookButton, 0, 3);
        selectedBookDisplay.add(editBookButton, 1, 3);
        selectedBookDisplay.add(undoDeletionButton, 2, 3);

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
        this.add(addButtons, 0, 2);
        this.add(bookListView, 0, 3);
        this.add(selectedBookDisplay, 1, 3);
        this.add(audioControls, 1, 4);

        // Set actions for buttons and listview
        addBookButton.setOnAction(this::addBookAction);
        addAudiobookButton.setOnAction(this::addAudiobookAction);
        authorInput.setOnKeyPressed(this::onEnterKeyPress);
        titleInput.setOnKeyPressed(this::onEnterKeyPress);
        deleteBookButton.setOnAction(this::deleteBookAction);
        editBookButton.setOnAction(this::editBookAction);
        bookListView.setOnMouseClicked(this::bookSelectedAction);
        undoDeletionButton.setOnMouseClicked(this::undoDeletion);
        saveTimestamp.setOnAction(this::saveTimeStampAction);

        refreshBookmarks();
    }

    private void saveTimeStampAction(ActionEvent e){
        if (mediaPlayer == null) return;

        System.out.println("Timestamp: "+beautifyDuration(mediaPlayer.currentTimeProperty().get()));
    }

    private void mediaPlayerStopAction(ActionEvent e){
        if (mediaPlayer == null) return;

        mediaPlayer.stop();
    }

    private void mediaPlayerPlayAction(ActionEvent e){
        if (mediaPlayer == null) return;

        if (mediaPlayer.statusProperty().getValue() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    private void bookSelectedAction(javafx.scene.input.MouseEvent e) {
        BookSuper selectedBook = getSelectedBook();
        if (selectedBook == null) return;

        setBookInfoText(selectedBook);

        if(selectedBook instanceof Audiobook)
            createNewMediaPlayer((Audiobook)selectedBook);

        System.out.println(bookmarks.getBookmarks());
    }

    private void onEnterKeyPress(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            addBookAction(e);
        }
    }

    private void addAudiobookAction(Event e) {
        File mp3 = fileSelector.openFileBrowser();
        System.out.println(mp3);
        Audiobook audiobook = new Audiobook(titleInput.getText(), authorInput.getText(), mp3);

        if(!audiobook.isEmpty() && addBook(audiobook)){
            refreshBookmarks();
            clearBookInput();

            createNewMediaPlayer(audiobook);
        }
    }

    private void onMediaPlayerTimeChange(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue){
        setDurationLabelValues(newValue, mediaPlayer.getMedia().durationProperty().get());
    }

    private void setDurationLabelValues(Duration current, Duration max){
        durationLabel.setText(beautifyDuration(current)+" / " + beautifyDuration(max));
    }

    private void addBookAction(Event e) {
        Book book = new Book(titleInput.getText(), authorInput.getText());
        if (book.isEmpty())
            return;

        if (addBook(book)) {
            refreshBookmarks();
            clearBookInput();
        } else {
            showNewAlert("Book exists", "The database already contains this book");
        }
    }

    private void editBookAction(ActionEvent e) {
        BookSuper selectedBook = getSelectedBook();

        if (selectedBook == null) {
            showNewAlert("Not selected", "No book has been selected");
        } else if (editTitleField.getText().isBlank() && editAuthorField.getText().isBlank()) {
            showNewAlert("Fields are empty", "Title and/or author missing");
        } else {
            // If a field is empty, old value is kept
            String newTitle = !editTitleField.getText().isBlank() ? editTitleField.getText() : selectedBook.getTitle();
            String newAuthor = !editAuthorField.getText().isBlank() ? editAuthorField.getText() : selectedBook.getAuthor();

            BookSuper sBook;
            if (selectedBook instanceof Audiobook)
                sBook = new Audiobook(newTitle, newAuthor, ((Audiobook)selectedBook).getMp3());
            else if (selectedBook instanceof Book)
                sBook = new Book(newTitle, newAuthor);
            else
                throw new Error("Selected book is not of any known book type");

            if(editBook(selectedBook, sBook)){
                refreshBookmarks();
                clearBookInfoText();
            } else {
                showNewAlert("Book conflict", "A book exists with that information already");
            }
        }
    }

    private void deleteBookAction(ActionEvent e) {
        BookSuper selectedBook = getSelectedBook();
        if (selectedBook == null) {
            showNewAlert("Not selected", "No book has been selected");
        } else if (deleteBook(selectedBook)) {
            refreshBookmarks();
            clearBookInfoText();
            undoDeletionButton.setVisible(true);
        }
    }
    
    private void undoDeletion(Event e) {
        if (deletedBook == null)
            return;

        addBook(deletedBook);
        refreshBookmarks();
        undoDeletionButton.setVisible(false);
    }

    private void clearBookInput() {
        authorInput.setText("");
        titleInput.setText("");
    }

    private void clearBookInfoText() {
        editAuthorField.setText("");
        editTitleField.setText("");
        audiobookName.setText("");
    }

    private void setBookInfoText(BookSuper book) {
        String author = book.getAuthor();
        String title = book.getTitle();
        String audiobookInfo = book instanceof Audiobook ? ((Audiobook) book).getMp3().getName() : "";

        editAuthorField.setText(author);
        editTitleField.setText(title);
        audiobookName.setText(audiobookInfo);
    }

    private BookSuper getSelectedBook() {
        return bookListView.getSelectionModel().getSelectedItem();
    }

    private void showNewAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void refreshBookmarks() {
        bookListView.getItems().clear();
        bookListView.getItems().addAll(bookmarks.getBookmarks());
    }

    private Boolean addBook(BookSuper book) {
        if (!bookmarks.contains(book)) {
            bookmarks.addBookmark(book);
            return true;
        }
        return false;
    }

    private Boolean deleteBook(BookSuper selectedBook) {
        if (bookmarks.contains(selectedBook)) {
            deletedBook = selectedBook;
            bookmarks.removeBookmark(selectedBook);
            return true;
        }
        return false;
    }

    private Boolean editBook(BookSuper selectedBook, BookSuper updatedBook) {
        if(!bookmarks.contains(updatedBook)) {
            bookmarks.updateBookmark(selectedBook, updatedBook);
            return true;
        }
        return false;
    }

    public Bookmarks getBookmarks() {
        return bookmarks;
    }

    private void createNewMediaPlayer(Audiobook audiobook){
        if(mediaPlayer != null)
            mediaPlayer.stop();

        Media hit = new Media(audiobook.getMp3().toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.currentTimeProperty().addListener(this::onMediaPlayerTimeChange);
    }

    private String beautifyDuration(Duration duration){
        if(duration == null) return "0:00:00";

        long seconds = (long)duration.toSeconds();
        return String.format("%d:%02d:%02d", seconds/3600, (seconds%3600)/60, (seconds%60));
    }
}