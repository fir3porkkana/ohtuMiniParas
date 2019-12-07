package ohtu.objects;

import java.io.File;
import java.util.Comparator;

/**
 * BookSuper
 */
public abstract class BookSuper implements Comparable<BookSuper> {

    protected int id;
    protected String author;
    protected String title;
    protected File cover;

    public String getAuthor() {
        return author;
    }

    public File getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEmpty() {
        if (this.author.isEmpty() || this.title.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return title + " by: " + author;
    }

    @Override
    public boolean equals(Object bookToCompare) {
        if (!(bookToCompare instanceof BookSuper))
            return false;
        BookSuper book = (BookSuper) bookToCompare;
        return ((book.getAuthor()).equalsIgnoreCase(author) && book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public int compareTo(BookSuper book) {
        return Comparator.comparing(BookSuper::getAuthor, (author1, author2) -> {
            return author1.toLowerCase().compareTo(author2.toLowerCase());
        }).thenComparing(BookSuper::getTitle, (title1, title2) -> {
            return title1.toLowerCase().compareTo(title2.toLowerCase());
        }).compare(this, book);
    }

    public boolean equalsCaseSensitive(Object bookToCompare) {
        if (!(bookToCompare instanceof BookSuper))
            return false;
        BookSuper book = (BookSuper) bookToCompare;
        return ((book.getAuthor()).equals(author) && book.getTitle().equals(title));
    }
}
