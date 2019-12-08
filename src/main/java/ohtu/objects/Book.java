package ohtu.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Book extends BookSuper {
    //private static String highlight = "";

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

    public File getCover() {
        return cover;
    }

    // We can replace each highlighted character with a bolded unicode version, to get a little better looking highlighting
    public static String getStylizedString(String masterString, String highlight){
        if(highlight.isBlank()) return masterString;

        List<Integer> indexes = new ArrayList<>();
        //String masterString = toString();
        String tMaster = masterString.toLowerCase();
        String tHighlight = highlight.toLowerCase();

        int currentIndex = 0;
        while(true){
            int i = tMaster.indexOf(tHighlight, currentIndex);
            if(i >= 0){
                indexes.add(i);
                currentIndex = i + 1;
            } else {
                break;
            }
        }

        if(indexes.size() == 0) return masterString;

        StringBuilder newString = new StringBuilder(masterString.substring(0, indexes.get(0)));

        /*newString.append("[");
        newString.append(masterString.toUpperCase(), indexes.get(0), indexes.get(0) + highlight.length());
        newString.append("]");*/
        newString.append(highlightReplacement(highlight));

        for( int i = 1; i < indexes.size(); i++ ){
            newString.append(masterString, indexes.get(i - 1) + highlight.length(), indexes.get(i));

            /*newString.append("[");
            newString.append(masterString.toUpperCase(), indexes.get(i), indexes.get(i) + highlight.length());
            newString.append("]");*/
            newString.append(highlightReplacement(highlight));
        }


        int dou = indexes.get(indexes.size() - 1) + highlight.length();
        if(dou < masterString.length() ){
            newString.append(
                    masterString.substring(dou)
            );
        }


        return newString.toString();
    }

    private static String highlightReplacement(String highlight){
        // We can replace each highlighted character with a bolded unicode version for example
        return "["+ highlight.toUpperCase()+"]";
    }
}