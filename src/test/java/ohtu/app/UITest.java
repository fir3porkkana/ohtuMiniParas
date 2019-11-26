package ohtu.app;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import ohtu.dao.BookDao;
import ohtu.objects.Bookmarks;

import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;

public class UITest extends ApplicationTest {
  static Bookmarks bookmarks;
  static BookDao bookDao = new BookDao("test.db");

  @BeforeClass
  public static void setUpClass() {

    bookmarks = new Bookmarks(bookDao);
    bookmarks.init();
  }

  @Before
  public void setUp() {
    try {
      bookDao.emptyTable();

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

  @Test
  public void containsButtonToAddBook() {
    FxAssert.verifyThat(".button", LabeledMatchers.hasText("Add book"));
  }

  @Test
  public void addingBookGoesToDatabase() {

  }

}