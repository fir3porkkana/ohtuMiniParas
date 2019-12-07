package ohtu.objects;

import ohtu.dao.*;
import ohtu.interfaces.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookmarksTest {
    Bookmarks bookmarks;

    Dao fakeDao = new Dao<BookSuper, String>() {
        ArrayList<BookSuper> books = new ArrayList<>();

        public void create(BookSuper book) {
            books.add(book);
        }

        public Book read(String s) {
            return new Book(s, null);
        }

        public void update(BookSuper b, BookSuper updatedBook) {
            int index = books.indexOf(b);
            books.set(index, updatedBook);
        }

        public void delete(BookSuper b) {
            books.remove(b);
        }

        public List<BookSuper> list() {
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

    @Test
    public void containsAudioBook() {
        Book testBook1 = new Book("The Conquest of Bread", "Peter Kropotkin");
        Audiobook testBook2 = new Audiobook("Audio", "tester", new File(""));
        Book testBook3 = new Book("Useless testbook", "some tester");
        Audiobook testBook4 = new Audiobook("Corrupt", "Hobo", new File(""));

        bookmarks.addBookmark(testBook1);
        bookmarks.addBookmark(testBook2);
        bookmarks.addBookmark(testBook3);

        assertTrue(bookmarks.containsAudioBook(testBook2));

        assertFalse(bookmarks.containsAudioBook(testBook4));
    }

    @Test
    public void searchingBookmarksReturnsCorrectList() {
        // Books in correct order 1,2,3,4
        Book book1 = new Book("Ants", "Adam");
        Book book2 = new Book("Bears", "Adam");
        Book book3 = new Book("First", "Bert");
        Book book4 = new Book("Second", "Bert");
        Book book5 = new Book("ABC", "Charlie");
        // Added in wrong order
        bookmarks.addBookmark(book5);
        bookmarks.addBookmark(book4);
        bookmarks.addBookmark(book3);
        bookmarks.addBookmark(book2);
        bookmarks.addBookmark(book1);

        List<BookSuper> result = bookmarks.searchBookmarks("be");

        // Books in expected order
        List<BookSuper> expected = new ArrayList<>();
        expected.add(book2);
        expected.add(book3);
        expected.add(book4);

        System.out.println("expected: " + expected);
        System.out.println("res: " + result);

        assertEquals(expected, result);

        assertFalse(result.contains(book1));
        assertFalse(result.contains(book5));

    }

}