package ohtu.objects;

public class Book {
    private int id;
    private String author;
    private String title;

    public Book(String title, String author) {
        this.title = title.trim();
        this.author = author.trim();
    }

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
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
    public boolean equals(Object book) {
        if (!book.getClass().equals(this.getClass())) {
            return false;
        }
        Book compareBook = (Book) (book);
        return ((compareBook.getAuthor()).equals(author) && compareBook.getTitle().equals(title));
    }
}