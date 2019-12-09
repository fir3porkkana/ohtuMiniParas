package ohtu.app;

import ohtu.objects.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import ohtu.dao.BookDao;
import ohtu.objects.Bookmarks;

import org.testfx.api.FxAssert;
import static org.junit.Assert.*;

import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.query.NodeQuery;

public class UITest extends ApplicationTest {
  static Bookmarks bookmarks;
  static BookDao bookDao = new BookDao("test.db");

  @BeforeClass
  public static void setUpClass() {

    bookmarks = new Bookmarks(bookDao);
    bookmarks.init();
  }

  @Before
  public void init() {
    try {
      bookDao.emptyTable();
      bookmarks.emptyBookmarks();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  /**
   * Will be called with {@code @Before} semantics, i. e. before each test method.
   */
  @Override
  public void start(Stage stage) {

    UI ui = new UI(bookmarks);
    ui.start(stage);
  }

  /*
   * @Test public void containsButtonToAddBook() { FxAssert.verifyThat(".button",
   * LabeledMatchers.hasText("Add book")); }
   */

  @Test
  public void bookGetsAddedToDatabase() {
    addBook("a", "a");
    ListView listview = find("#bookList");
    System.out.println(listview.getItems());
    assertThat(listview, ListViewMatchers.hasListCell(new Book("a", "a")));
  }

  @Test
  public void listviewIsEmptyUponStartOfTests() {
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.hasItems(0));
  }

  @Test
  public void multiplesOfSameBookDontGetAdded() {
    addBook("b", "b");
    addBook("b", "b");
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.hasItems(1));
  }

  @Test
  public void selectingBooksFromDatabaseWorks() {
    addBook("a", "a");
    ListView listview = find("#bookList");

    clickOn("a by: a");
    System.out.println(listview.getEditingIndex());
    System.out.println(listview.getItems());
    TextField authorText = find("#edit_author");
    TextField titleText = find("#edit_title");
    assertEquals("a", authorText.getText());
    assertEquals("a", titleText.getText());
  }

  @Test
  public void deletingBooksFromDatabaseWorks() {
    addBook("a", "a");
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.hasListCell(new Book("a", "a")));

    clickOn("a by: a");
    clickOn("#delete_button");
    assertThat(listview, ListViewMatchers.isEmpty());
  }

  @Test
  public void editingWorksWithBothFieldsFilled() {
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.isEmpty());
    addBook("a", "a");

    clickOn("a by: a");
    TextField authorText = find("#edit_author");
    authorText.setText("b");
    TextField titleText = find("#edit_title");
    titleText.setText("b");
    clickOn("#edit_button");

    assertThat(listview, ListViewMatchers.hasListCell(new Book("b", "b")));
  }

  @Test
  public void editingWorksWithOnlyTitleInput() {
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.isEmpty());
    addBook("a", "a");
    clickOn("a by: a");
    TextField titleText = find("#edit_title");
    titleText.setText("b");

    TextField authorText = find("#edit_author");
    authorText.setText("");
    clickOn("#edit_button");

    assertThat(listview, ListViewMatchers.hasListCell(new Book("b", "a")));
  }

  @Test
  public void titleTextGetsInputCorrectlyOnSelectingBook() {
    ListView listview = find("#bookList");
    addBook("a", "b");
    clickOn("a by: b");

    TextField titleText = find("#edit_title");
    assertEquals("a", titleText.getText());
  }

  @Test
  public void authorTextGetsInputCorrectlyOnSelectingBook() {
    // ListView listview = find("#bookList");
    addBook("a", "b");
    clickOn("a by: b");

    TextField authorText = find("#edit_author");
    assertEquals("b", authorText.getText());
  }

  @Test
  public void editingWorksWithOnlyAuthorInput() {
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.isEmpty());
    addBook("a", "a");
    clickOn("a by: a");
    TextField authorText = find("#edit_author");
    authorText.setText("b");

    TextField titleText = find("#edit_title");
    titleText.setText("");
    clickOn("#edit_button");

    assertThat(listview, ListViewMatchers.hasListCell(new Book("a", "b")));
  }

  @Test
  public void editingWorksWithNoTitleAndAuthorInput() {
    ListView listview = find("#bookList");
    assertThat(listview, ListViewMatchers.isEmpty());
    addBook("a", "a");
    clickOn("a by: a");

    TextField authorText = find("#edit_author");
    authorText.setText("");

    TextField titleText = find("#edit_title");
    titleText.setText("");

    clickOn("#edit_button");

    assertThat(listview, ListViewMatchers.hasListCell(new Book("a", "a")));
  }

  @Test
  public void searchReturnsCorrectList() {
    ListView listview = find("#bookList");

    addBook("title", "AUTHOR");
    addBook("hidden", "hidden");
    addBook("title2", "arthur");

    assertThat(listview, ListViewMatchers.hasItems(3));

    clickOn("#search_input");
    write("t");

    assertThat(listview, ListViewMatchers.hasItems(2));

    // --- Hidden kirja ei tuu takas vaikka search input menee tyhjäks ---
    // clickOn("#search_input");
    // press(KeyCode.BACK_SPACE);
    // TextField search = find("#search_input");
    // System.out.println(listview.getItems());
    // System.out.println("input: " + search.getText());

    // assertThat(listview, ListViewMatchers.hasItems(3));
  }

  @Test
  public void undoButtonWorks() {
    ListView listview = find("#bookList");
    addBook("title", "arthour");
    clickOn("title by: arthour");
    clickOn("#delete_button");
    clickOn("#undo_button");

    assertThat(listview, ListViewMatchers.hasListCell(new Book("title", "arthour")));
  }

  @Test
  public void undoButtonNotVisible() {
    addBook("title", "arthuar");
    Button undoButton = find("#undo_button");
    assertFalse(undoButton.isVisible());
  }

  public void addBook(String title, String author) {
    TextField titleText = find("#title_input");
    titleText.setText(title);

    TextField authorText = find("#author_input");
    authorText.setText(author);

    clickOn("#submit_button");
  }

  public <T extends Node> T find(final String query) {
    /** TestFX provides many operations to retrieve elements from the loaded GUI. */
    return lookup(query).query();
  }
}