package ohtu.objects;

import java.io.File;

/**
 * Audiobook
 */
public class Audiobook extends BookSuper {

    private File mp3;

    public Audiobook(String title, String author, File mp3) {
        this.title = title.trim();
        this.author = author.trim();
        this.mp3 = mp3;
    }

    public Audiobook(int id, String title, String author, File mp3) {
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
        this.mp3 = mp3;
    }

    public File getMp3() {
        return mp3;
    }
    
    @Override
    public boolean equals(Object b) {
        if (b == null) {
            return false;
        }
        Audiobook book = (Audiobook) b;
        return ((book.getAuthor()).equals(author) && book.getTitle().equals(title) && book.getMp3().equals(mp3));
    }

    @Override
    public boolean isEmpty() {
        if (this.author.isEmpty() || this.title.isEmpty() || this.mp3 == null) {
            return true;
        }
        return false;
    }
}