package ohtu.objects;

public class Book {
    private int id;
    private String author;
    private String title;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title + " by: " + author;
    }

    public Boolean equals(Book book) {
        return (book.getAuthor().equals(author) && book.getTitle().equals(title));
    }
}