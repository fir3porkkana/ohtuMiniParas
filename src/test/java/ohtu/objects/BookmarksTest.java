package ohtu.objects;

import ohtu.dao.*;
import ohtu.interfaces.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class BookmarksTest {
    Bookmarks bookmarks;

    Dao fakeDao = new Dao<Book, String>() {
        ArrayList<Book> books = new ArrayList<>();

        public void create(Book book) {
            books.add(book);
        }

        public Book read(String s) {
            return new Book(s, null);
        }

        public void update(Book b, Book updatedBook) {
            int index = books.indexOf(b);
            books.set(index, updatedBook);
        }

        public void delete(Book b) {
            books.remove(b);
        }

        public List<Book> list() {
            return books;
        }
    };

    @Before
    public void setUp() {
        bookmarks = new Bookmarks(fakeDao);
    }

    /*
     * static Bookmarks bookmarks; static BookDao bookDao;
     * 
     * @Before public void setUp() { bookmarks = new Bookmarks(bookDao); try {
     * bookDao.emptyTable();
     * 
     * } catch (Exception e) { // TODO: handle exception } }
     * 
     * @BeforeClass public static void setUpClass() { bookDao = new
     * BookDao("test.db");
     * 
     * >>>>>>> 66267e4e851b00dcdc28ea63a26bf653607ff27d }
     */

    @Test
    public void addingABookIncrementsSizeByOne() {
        Book testBook = new Book("Why socialism?", "Albert Einstein");
        bookmarks.addBookmark(testBook);

        assertEquals(1, bookmarks.getBookmarks().size());
    }

    @Test
    public void removingABookDecrementsSizeByOne() {
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        Book anotherTestBook = new Book("Democracy for the Few", "Michael Parenti");
        bookmarks.addBookmark(testBook);
        bookmarks.addBookmark(anotherTestBook);

        assertEquals(2, bookmarks.getBookmarks().size());

        bookmarks.removeBookmark(testBook);

        assertEquals(1, bookmarks.getBookmarks().size());
    }

    @Test
    public void removingANonExistingBookDoesntDecrementSize() {
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        Book anotherTestBook = new Book("Democracy for the Few", "Michael Parenti");
        bookmarks.addBookmark(testBook);
        bookmarks.addBookmark(anotherTestBook);

        assertEquals(2, bookmarks.getBookmarks().size());

        bookmarks.removeBookmark(new Book("Why socialism?", "Albert Einstein"));

        assertEquals(2, bookmarks.getBookmarks().size());
    }

    @Test
    public void addingABookAddsItToTheBookmarksList() {
        Book anotherTestBook = new Book("yes", "on");
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        bookmarks.addBookmark(anotherTestBook);
        bookmarks.addBookmark(testBook);

        Assert.assertTrue(bookmarks.getBookmarks().contains(testBook));
    }

    @Test
    public void removingABookRemovesItFromTheBookmarksList() {
        Book anotherTestBook = new Book("yes", "on");
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        bookmarks.addBookmark(testBook);
        bookmarks.addBookmark(anotherTestBook);
        bookmarks.removeBookmark(testBook);

        Assert.assertFalse(bookmarks.getBookmarks().contains(testBook));
    }

    @Test
    public void updatingABookRemovesOld() {
        Book anotherTestBook = new Book("yes", "on");
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        bookmarks.addBookmark(testBook);
        bookmarks.addBookmark(anotherTestBook);
        bookmarks.updateBookmark(testBook, new Book("Why socialism", "Albert Einstein"));

        Assert.assertFalse(bookmarks.getBookmarks().contains(testBook));
    }

    @Test
    public void updatingABookAddsNew() {
        Book anotherTestBook = new Book("yes", "on");
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        Book updatedBook = new Book("Why socialism", "Albert Einstein");
        bookmarks.addBookmark(testBook);
        bookmarks.addBookmark(anotherTestBook);
        bookmarks.updateBookmark(testBook, updatedBook);
        bookmarks.removeBookmark(testBook);

        Assert.assertTrue(bookmarks.getBookmarks().contains(updatedBook));
    }

    @Test
    public void getBookmarksReturnsCorrectList() {
        ArrayList<Book> correctList = new ArrayList<>();
        Book anotherTestBook = new Book("yes", "on");
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        bookmarks.addBookmark(testBook);
        bookmarks.addBookmark(anotherTestBook);
        correctList.add(testBook);
        correctList.add(anotherTestBook);
        // System.out.println("inBookMarks: " + bookmarks.getBookmarks());

        assertEquals(bookmarks.getBookmarks(), correctList);
    }

    @Test
    public void bookmarksContainsReturnsRightValue() {
        Book testBook = new Book("The Conquest of Bread", "Peter Kropotkin");
        bookmarks.addBookmark(testBook);

        assertTrue(bookmarks.contains(testBook));

        Book anotherTestBook = new Book("Democracy for the Few", "Michael Parenti");

        assertFalse(bookmarks.contains(anotherTestBook));
    }

}