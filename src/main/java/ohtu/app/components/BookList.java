package ohtu.app.components;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ohtu.app.FileSelector;
import ohtu.objects.*;

public class BookList extends GridPane {

    private Bookmarks bookmarks;
    private BookSuper deletedBook;

    private TextField authorInput = new TextField();
    private TextField titleInput = new TextField();

    private ListView<BookSuper> bookListView = new ListView<>();

    private Button undoDeletionButton;
    private TextField editTitleField = new TextField();
    private TextField editAuthorField = new TextField();
    private Label audiobookName = new Label();
    private ListView<Timestamp> timestampListView = new ListView<>();

    private FileSelector fileSelector;
    private Label mediaFile = new Label("");
    private MediaPlayer mediaPlayer;
    private Audiobook mediaBook;

    private Label durationLabel;
    private Slider progressBar;

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
        deleteBookButton.setMinWidth(85);
        Button editBookButton = new Button("Save change");
        editBookButton.setId("edit_button");
        editBookButton.setMinWidth(85);
        undoDeletionButton = new Button("Undo");
        undoDeletionButton.setId("undo_button");
        undoDeletionButton.setVisible(false);

        editAuthorField.setPromptText("Set new Author");
        editAuthorField.setId("edit_author");
        editTitleField.setPromptText("Set new Title");
        editTitleField.setId("edit_title");

        timestampListView.setVisible(false);
        timestampListView.managedProperty().bind(timestampListView.visibleProperty());

        // Display for audio controls
        GridPane audioControls = new GridPane();
        Button playButton = new Button("\u25b6\u25ae\u25ae");

        Button stopButton = new Button("\u25a0");
        this.progressBar = new Slider();

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

        progressBar.setMin(0);
        progressBar.setMax(1);
        progressBar.setPadding(new Insets(10, 5, 10, 5));
        progressBar.setPickOnBounds(false);

        progressBar.setOnMouseReleased(this::progressBarMouseRelease);

        durationLabel = new Label();
        Button saveTimestamp = new Button("Timestamp");

        audioControls.add(playButton, 0, 0);
        audioControls.add(stopButton, 1, 0);
        audioControls.add(saveTimestamp, 2, 0);
        audioControls.add(progressBar, 0, 1);
        audioControls.add(durationLabel, 1, 1);
        audioControls.add(mediaFile, 0,2, 2, 1);

        // audioControls.getChildren().addAll(playButton, stopButton, saveTimestamp,
        // durationLabel, progressBar);

        selectedBookDisplay.add(deleteBookButton, 0, 0);
        selectedBookDisplay.add(editBookButton, 1, 0);
        selectedBookDisplay.add(undoDeletionButton, 2, 0);

        selectedBookDisplay.add(new Label("Title"), 0, 1);
        selectedBookDisplay.add(new Label("Author"), 0, 2);
        selectedBookDisplay.add(editTitleField, 1, 1);
        selectedBookDisplay.add(editAuthorField, 1, 2);
        selectedBookDisplay.add(audiobookName, 1, 3);
        selectedBookDisplay.add(timestampListView, 0, 4, 2, 1);

        // Setting size for the pane
        this.setMinSize(400, 200);

        // Setting the padding
        this.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        bookListView.setId("bookList");
        bookListView.setMinWidth(250);

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
        timestampListView.setOnMouseClicked(this::timeStampSelectedAction);
        undoDeletionButton.setOnMouseClicked(this::undoDeletion);
        saveTimestamp.setOnAction(this::saveTimeStampAction);

        refreshBookmarks();
    }

    private void progressBarMouseRelease(MouseEvent event) {
        if (mediaPlayer == null) {
            return;
        }
        int milliseconds = (int) (progressBar.getValue() * mediaPlayer.getTotalDuration().toMillis());
        Duration duration = new Duration(milliseconds);
        mediaPlayer.seek(duration);

    }

    private void saveTimeStampAction(ActionEvent e) {
        if (mediaPlayer == null)
            return;

        //System.out.println("Timestamp: " + Timestamp.durationToString(mediaPlayer.currentTimeProperty().get()));
        BookSuper book = getSelectedBook();
        if(!(book instanceof Audiobook)) return;

        Audiobook aBook = (Audiobook)book;
        Timestamp t = new Timestamp(mediaPlayer.currentTimeProperty().get());
        if(addTimestamp(aBook,t)){
            aBook.addTimestamp(t);
            refreshTimeStampList(aBook);
        }
    }

    private void mediaPlayerStopAction(ActionEvent e) {
        if (mediaPlayer == null)
            return;

        mediaPlayer.stop();
    }

    private void mediaPlayerPlayAction(ActionEvent e) {
        if (mediaBook != getSelectedBook() && getSelectedBook() instanceof Audiobook){
            createNewMediaPlayer((Audiobook)getSelectedBook());
        }
        if(mediaPlayer == null) return;

        if (mediaPlayer.statusProperty().getValue() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    private void timeStampSelectedAction(MouseEvent e){
        Timestamp selectedStamp = timestampListView.getSelectionModel().getSelectedItem();
        if(selectedStamp == null || mediaPlayer == null ) return;

        mediaPlayer.seek(selectedStamp.getDuration());
    }

    private void bookSelectedAction(MouseEvent e) {
        BookSuper selectedBook = getSelectedBook();
        if (selectedBook == null)
            return;

        setBookInfoText(selectedBook);
        if (selectedBook instanceof Audiobook){
            //createNewMediaPlayer((Audiobook) selectedBook);
            refreshTimeStampList((Audiobook) selectedBook);
        }

        timestampListView.setVisible(selectedBook instanceof Audiobook);
        timestampListView.managedProperty().bind(timestampListView.visibleProperty());

        System.out.println(bookmarks.getBookmarks());
    }

    private void refreshTimeStampList(Audiobook book){
        timestampListView.getItems().clear();
        timestampListView.getItems().addAll(book.getTimestampList());
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

        if (!audiobook.isEmpty() && addBook(audiobook)) {
            refreshBookmarks();
            clearBookInput();

            //createNewMediaPlayer(audiobook);
        }
    }

    private void onMediaPlayerTimeChange(ObservableValue<? extends Duration> observable, Duration oldValue,
            Duration newValue) {
        if(mediaPlayer == null || mediaPlayer.getMedia() == null) return;
        Duration length = mediaPlayer.getMedia().durationProperty().get();
        setDurationLabelValues(newValue, length);

        if (newValue != null && length != null  && !progressBar.isPressed()) {
            progressBar.setValue(newValue.toMillis() / length.toMillis());
        }
    }

    private void setDurationLabelValues(Duration current, Duration max) {
        durationLabel.setText(Timestamp.durationToString(current) + " / " + Timestamp.durationToString(max));
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
            String newAuthor = !editAuthorField.getText().isBlank() ? editAuthorField.getText()
                    : selectedBook.getAuthor();

            BookSuper sBook;
            if (selectedBook instanceof Audiobook)
                sBook = new Audiobook(newTitle, newAuthor, ((Audiobook) selectedBook).getMp3());
            else if (selectedBook instanceof Book)
                sBook = new Book(newTitle, newAuthor);
            else
                throw new Error("Selected book is not of any known book type");

            if (editBook(selectedBook, sBook)) {
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

    private Boolean addTimestamp(BookSuper book, Timestamp timestamp){
        if(!(book instanceof Audiobook) || timestamp == null) return false;
        bookmarks.addTimestamp((Audiobook) book, timestamp);
        return true;
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
        // 2 duplicates can't exist even if they have different casing, but a books case
        // can be edited
        if (!bookmarks.contains(updatedBook)
                || (selectedBook.equals(updatedBook) && !selectedBook.equalsCaseSensitive(updatedBook))) {
            bookmarks.updateBookmark(selectedBook, updatedBook);
            return true;
        }
        return false;
    }

    public Bookmarks getBookmarks() {
        return bookmarks;
    }

    private void createNewMediaPlayer(Audiobook audiobook) {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }

        mediaBook = audiobook;
        mediaFile.setText("Playing: "+mediaBook.toString());

        Media hit = new Media(audiobook.getMp3().toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setOnReady(()->setDurationLabelValues(Duration.ZERO, hit.durationProperty().get()));
        mediaPlayer.currentTimeProperty().addListener(this::onMediaPlayerTimeChange);
    }
}