package ohtu.objects;
import ohtu.dao.*;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BookmarksTest {
    Bookmarks bookmarks;

    @Before
    public void setUp() {
        bookmarks = new Bookmarks(new BookDao());
    }

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
}