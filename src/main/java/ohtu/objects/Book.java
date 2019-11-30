package ohtu.objects;

public class Book extends BookSuper {

    public Book(String title, String author) {
        this.title = title.trim();
        this.author = author.trim();
    }

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
    }

}