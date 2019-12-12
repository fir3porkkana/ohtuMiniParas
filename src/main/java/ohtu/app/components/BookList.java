package ohtu.app.components;

import java.io.File;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ohtu.app.FileSelector;
import ohtu.objects.*;
import javafx.beans.binding.*;

public class BookList extends GridPane {

    private Bookmarks bookmarks;
    private BookSuper deletedBook;

    private TextField authorInput = new TextField();
    private TextField titleInput = new TextField();

    private TextField searchInput = new TextField();

    private ListView<BookSuper> bookListView = new ListView<>();

    private GridPane selectedBookDisplay;
    private Button undoDeletionButton;
    private Button editBookButton;
    private Button deleteBookButton;

    private Button playButton;

    private VBox audioControls;
    private TextField editTitleField = new TextField();
    private TextField editAuthorField = new TextField();
    private Label audiobookName = new Label();
    private ListView<Timestamp> timestampListView = new ListView<>();

    private FileSelector fileSelector;
    private Label mediaFile = new Label("");
    private MediaPlayer mediaPlayer;
    private Audiobook bookCurrentlyPlaying;

    private Label durationLabel;
    private File selectedCover;
    ImageView imageView = new ImageView();
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

        Button addCoverButton = new Button("Add book cover");

        searchInput.setId("search_input");
        searchInput.setPromptText("Search Books");
        searchInput.setOnKeyTyped(this::onSearchInput);

        HBox addButtons = new HBox(10);
        Button addBookButton = new Button("Add book");
        Button addAudiobookButton = new Button("Add audiobook");
        addBinding(addBookButton,titleInput, authorInput);
        addBinding(addAudiobookButton, titleInput, authorInput);
        addBookButton.setId("submit_button");
        addAudiobookButton.setId("submit_audio_button");
        addButtons.getChildren().addAll(addBookButton, addAudiobookButton);

        // Display for selected book
        this.selectedBookDisplay = new GridPane();
        selectedBookDisplay.setPadding(new Insets(10, 10, 10, 10));
        selectedBookDisplay.setVgap(5);
        selectedBookDisplay.setHgap(5);
        selectedBookDisplay.setDisable(true);

        this.deleteBookButton = new Button("Delete book");
        deleteBookButton.setId("delete_button");
        deleteBookButton.setMinWidth(85);
        this.editBookButton = new Button("Save changes");
        editBookButton.setId("edit_button");
        editBookButton.setMinWidth(85);

        undoDeletionButton = new Button("Undo");
        undoDeletionButton.setId("undo_button");
        undoDeletionButton.setVisible(false);

        editAuthorField.setPromptText("Set new Author");
        editAuthorField.setId("edit_author");
        editTitleField.setPromptText("Set new Title");
        editTitleField.setId("edit_title");
        // Display for audio controls

        this.playButton = new Button("\u25b6");

        Button stopButton = new Button("\u25a0");
        this.progressBar = new Slider(0, 1, 0);
        progressBar.setPickOnBounds(false);
        progressBar.setMinWidth(500);

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

        progressBar.setOnMouseReleased(this::progressBarMouseRelease);

        durationLabel = new Label();
        Button saveTimestamp = new Button("Timestamp");

        imageView.setFitWidth(160);
        imageView.setFitHeight(160);

        HBox audioButtons = new HBox(10, playButton, stopButton, saveTimestamp, mediaFile);
        HBox audioSlider = new HBox(10, progressBar, durationLabel);
        this.audioControls = new VBox(10, audioButtons, audioSlider, timestampListView);
        audioButtons.setAlignment(Pos.CENTER_LEFT);

        audioControls.setDisable(true);

        selectedBookDisplay.add(deleteBookButton, 0, 0);
        selectedBookDisplay.add(editBookButton, 1, 0);
        selectedBookDisplay.add(undoDeletionButton, 2, 0);

        selectedBookDisplay.add(new Label("Title"), 0, 1);
        selectedBookDisplay.add(new Label("Author"), 0, 2);
        selectedBookDisplay.add(editTitleField, 1, 1);
        selectedBookDisplay.add(editAuthorField, 1, 2);
        selectedBookDisplay.add(audiobookName, 0, 3, 2, 1);

        selectedBookDisplay.add(imageView, 0, 4, 2, 3);

        // Setting the padding
        this.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        bookListView.setId("bookList");
        bookListView.setMinWidth(250);

        bookListView.setCellFactory(this::highlightCellFactory);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(5));

        // Arranging all the nodes in the grid
        this.add(titleLabel, 0, 0);
        this.add(titleInput, 1, 0);
        this.add(addCoverButton, 1, 2);
        this.add(authorLabel, 0, 1);
        this.add(authorInput, 1, 1);
        this.add(addButtons, 0, 2);
        this.add(searchInput, 0, 3);
        this.add(bookListView, 0, 4);
        this.add(selectedBookDisplay, 1, 4);
        this.add(separator, 0, 5, 2, 1);
        this.add(audioControls, 0, 6, 2, 1);

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
        addCoverButton.setOnAction(this::selectCoverAction);

        refreshBookmarks();

    }

    private ListCell<BookSuper> highlightCellFactory(ListView<BookSuper> bookSuperListView) {
        return new ListCell<BookSuper>() {
            @Override
            protected void updateItem(BookSuper item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty && item != null) {
                    setText(Book.getStylizedString(item.toString(), searchInput.getText()));
                } else {
                    setText(null);
                }
            }
        };
    }

    private void addBinding(Button addBookButton, TextField titleInput, TextField authorInput) {
        BooleanBinding titleInputValid = Bindings.createBooleanBinding(()-> {
           return !titleInput.getText().isEmpty();
        }, titleInput.textProperty());

        BooleanBinding authorInputValid = Bindings.createBooleanBinding(() -> {
            return !authorInput.getText().isEmpty();
        }, authorInput.textProperty());

        addBookButton.disableProperty().bind(titleInputValid.not().or(authorInputValid.not()));
    }

    private void autoPlayNextAudio() {
        int current = bookListView.getSelectionModel().getSelectedIndex();
        List<BookSuper> list = bookListView.getItems();

        for (int i = current + 1; i < list.size(); i++) {
            BookSuper book = list.get(i);
            if (book instanceof Audiobook) {
                bookListView.getSelectionModel().select(i);
                createNewMediaPlayer((Audiobook) book);
                mediaPlayer.play();
                break;
            }
        }

    }

    private void onSearchInput(Event e) {
        // Moved filtering to refreshBookmarks, so the filter stays after actions that
        // refresh list like editing a book
        refreshBookmarks();

        /*
         * if (searchInput.getText().isEmpty()) { refreshBookmarks(); } else {
         * bookListView.getItems().clear();
         * bookListView.getItems().addAll(bookmarks.searchBookmarks(searchInput.getText(
         * ))); }
         */
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

        // System.out.println("Timestamp: " +
        // Timestamp.durationToString(mediaPlayer.currentTimeProperty().get()));
        BookSuper book = getSelectedBook();

        Timestamp t = new Timestamp(mediaPlayer.currentTimeProperty().get());
        if (addTimestamp(bookCurrentlyPlaying, t)) {
            bookCurrentlyPlaying.addTimestamp(t);

            refreshTimeStampList(bookCurrentlyPlaying);

        }
    }

    private void mediaPlayerStopAction(ActionEvent e) {
        if (mediaPlayer == null)
            return;
        playButton.setText("\u25b6");
        mediaPlayer.stop();
    }

    private void mediaPlayerPlayAction(ActionEvent e) {
        if (bookCurrentlyPlaying != getSelectedBook() && getSelectedBook() instanceof Audiobook) {
            createNewMediaPlayer((Audiobook) getSelectedBook());
            //bookCurrentlyPlaying = (Audiobook) getSelectedBook();
        }
        if (mediaPlayer == null)
            return;

        if (mediaPlayer.statusProperty().getValue() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playButton.setText("\u25b6");
        } else {
            mediaPlayer.play();
            playButton.setText("\u25ae\u25ae");
        }
    }

    private void timeStampSelectedAction(MouseEvent e) {
        Timestamp selectedStamp = timestampListView.getSelectionModel().getSelectedItem();
        if (selectedStamp == null || mediaPlayer == null)
            return;

        mediaPlayer.seek(selectedStamp.getDuration());
    }

    private void bookSelectedAction(MouseEvent e) {
        BookSuper selectedBook = getSelectedBook();
        if (selectedBook == null)
            return;

        selectedBookDisplay.setDisable(false);

        deleteBookButton.setDisable(false);
        editBookButton.setDisable(false);

        setBookInfoText(selectedBook);
        setBookCoverToDisplay(selectedBook);
        if (selectedBook instanceof Audiobook) {
            audioControls.setDisable(false);
            //refreshTimeStampList((Audiobook) selectedBook); //Timestamplist is now linked to currently playing audio
        } else if (mediaPlayer == null) {
            audioControls.setDisable(true);
        }

        System.out.println(bookmarks.getBookmarks());
    }

    private void refreshTimeStampList(Audiobook book) {
        timestampListView.getItems().clear();
        timestampListView.getItems().addAll(book.getTimestampList());
    }

    private void onEnterKeyPress(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            addBookAction(e);
        }
    }

    private void addAudiobookAction(Event e) {

        File mp3 = fileSelector.openAudioBrowser();
        System.out.println(mp3);
        Audiobook audiobook = new Audiobook(titleInput.getText(), authorInput.getText(), mp3, selectedCover);

        if (!audiobook.isEmpty() && addBook(audiobook)) {
            refreshBookmarks();
            clearBookInput();
        }
    }

    private void onMediaPlayerTimeChange(ObservableValue<? extends Duration> observable, Duration oldValue,
            Duration newValue) {
        if (mediaPlayer == null || mediaPlayer.getMedia() == null)
            return;
        Duration length = mediaPlayer.getMedia().durationProperty().get();
        setDurationLabelValues(newValue, length);

        if (newValue != null && length != null && !progressBar.isPressed()) {
            progressBar.setValue(newValue.toMillis() / length.toMillis());
        }
    }

    private void setDurationLabelValues(Duration current, Duration max) {
        durationLabel.setText(Timestamp.durationToString(current) + " / " + Timestamp.durationToString(max));
    }

    private void addBookAction(Event e) {
        Book book = new Book(titleInput.getText(), authorInput.getText(), selectedCover);
        if (book.isEmpty())
            return;

        if (addBook(book)) {
            refreshBookmarks();
            clearBookInput();
            selectedCover = null;
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
                editBookButton.setDisable(true);
                deleteBookButton.setDisable(true);
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
            editBookButton.setDisable(true);
            deleteBookButton.setDisable(true);
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

    private void setBookCoverToDisplay(BookSuper book) {
        File cover = book.getCover();
        Image bookCover = null;
        if (cover != null) {
            bookCover = new Image(cover.toURI().toString());
        }
        imageView.setImage(bookCover);
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
        if (searchInput.getText().isEmpty()) {
            bookListView.getItems().addAll(bookmarks.getBookmarks());
        } else {
            bookListView.getItems().addAll(bookmarks.searchBookmarks(searchInput.getText()));
        }
    }

    private Boolean addBook(BookSuper book) {
        if (!bookmarks.contains(book)) {
            bookmarks.addBookmark(book);
            return true;
        }
        return false;
    }

    private Boolean addTimestamp(BookSuper book, Timestamp timestamp) {
        if (!(book instanceof Audiobook) || timestamp == null)
            return false;
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

    private void selectCoverAction(ActionEvent e) {

        selectedCover = this.fileSelector.openImageBrowser();
    }

    private void createNewMediaPlayer(Audiobook audiobook) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        bookCurrentlyPlaying = audiobook;
        mediaFile.setText("Playing: " + bookCurrentlyPlaying.toString());
        refreshTimeStampList(audiobook);

        Media hit = new Media(audiobook.getMp3().toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setOnReady(() -> setDurationLabelValues(Duration.ZERO, hit.durationProperty().get()));
        mediaPlayer.currentTimeProperty().addListener(this::onMediaPlayerTimeChange);
        mediaPlayer.setOnEndOfMedia(this::autoPlayNextAudio);

    }
}