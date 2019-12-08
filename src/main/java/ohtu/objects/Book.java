package ohtu.objects;

import com.sun.prism.image.CachingCompoundImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static String getStylizedString(String masterString, String highlight){
        if(highlight.isBlank()) return masterString;

        List<Integer> indexes = new ArrayList<>();

        String tMaster = masterString.toLowerCase();
        String tHighlight = highlight.toLowerCase();

        int currentIndex = 0;
        while(true){
            int i = tMaster.indexOf(tHighlight, currentIndex);
            if(i >= 0){
                indexes.add(i);
                currentIndex = i + highlight.length();
            } else {
                break;
            }
        }

        if(indexes.size() == 0) return masterString;

        StringBuilder newString = new StringBuilder(masterString.substring(0, indexes.get(0)));

        newString.append(highlightReplacement(masterString.substring(indexes.get(0), indexes.get(0)+highlight.length())));

        for( int i = 1; i < indexes.size(); i++ ){
            newString.append(masterString, indexes.get(i - 1) + highlight.length(), indexes.get(i));

            newString.append(highlightReplacement(masterString.substring(indexes.get(i), indexes.get(i)+highlight.length())));
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
        String s = "";

        for(int i = 0; i < highlight.length(); i++){
            s = s.concat(bolderMap.getOrDefault(highlight.charAt(i), ""+highlight.charAt(i)) );
        }

        return s;
        //return "["+ highlight.toUpperCase()+"]";
    }

    private static HashMap<Character, String> bolderMap = new HashMap<>(){{
        put('A', "\uD835\uDC00");
        put('B', "\uD835\uDC01");
        put('C', "\uD835\uDC02");
        put('D', "\uD835\uDC03");
        put('E', "\uD835\uDC04");
        put('F', "\uD835\uDC05");
        put('G', "\uD835\uDC06");
        put('H', "\uD835\uDC07");
        put('I', "\uD835\uDC08");
        put('J', "\uD835\uDC09");
        put('K', "\uD835\uDC0A");
        put('L', "\uD835\uDC0B");
        put('M', "\uD835\uDC0C");
        put('N', "\uD835\uDC0D");
        put('O', "\uD835\uDC0E");
        put('P', "\uD835\uDC0F");
        put('Q', "\uD835\uDC10");
        put('R', "\uD835\uDC11");
        put('S', "\uD835\uDC12");
        put('T', "\uD835\uDC13");
        put('U', "\uD835\uDC14");
        put('V', "\uD835\uDC15");
        put('W', "\uD835\uDC16");
        put('X', "\uD835\uDC17");
        put('Y', "\uD835\uDC18");
        put('Z', "\uD835\uDC19");
        //put('Å', "Å");
        //put('Ä', "Ä");
        //put('Ö', "Ö");

        put('a', "\uD835\uDC1A");
        put('b', "\uD835\uDC1B");
        put('c', "\uD835\uDC1C");
        put('d', "\uD835\uDC1D");
        put('e', "\uD835\uDC1E");
        put('f', "\uD835\uDC1F");
        put('g', "\uD835\uDC20");
        put('h', "\uD835\uDC21");
        put('i', "\uD835\uDC22");
        put('j', "\uD835\uDC23");
        put('k', "\uD835\uDC24");
        put('l', "\uD835\uDC25");
        put('m', "\uD835\uDC26");
        put('n', "\uD835\uDC27");
        put('o', "\uD835\uDC28");
        put('p', "\uD835\uDC29");
        put('q', "\uD835\uDC2A");
        put('r', "\uD835\uDC2B");
        put('s', "\uD835\uDC2C");
        put('t', "\uD835\uDC2D");
        put('u', "\uD835\uDC2E");
        put('v', "\uD835\uDC2F");
        put('w', "\uD835\uDC30");
        put('x', "\uD835\uDC31");
        put('y', "\uD835\uDC32");
        put('z', "\uD835\uDC33");
        //put('å', "å");
        //put('ä', "ä");
        //put('ö', "ö");
    }};
}