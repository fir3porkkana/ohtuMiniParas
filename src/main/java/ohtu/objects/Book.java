package ohtu.objects;

import java.io.File;

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

    public Book(String title, String author, File cover) {
        this.title = title.trim();
        this.author = author.trim();
        this.cover = cover;
    }

    public Book(int id, String title, String author, File cover) {
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
        this.cover = cover;
    }

}