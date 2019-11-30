package ohtu.objects;

/**
 * BookSuper
 */
public abstract class BookSuper {

    protected int id;
    protected String author;
    protected String title;

    public String getAuthor() {
        return author;
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
    public boolean equals(Object b) {
        if (b == null) {
            return false;
        }
        Book book = (Book) b;
        return ((book.getAuthor()).equals(author) && book.getTitle().equals(title));
    }

}