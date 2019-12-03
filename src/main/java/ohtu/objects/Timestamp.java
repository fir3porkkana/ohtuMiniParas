package ohtu.objects;

import java.time.Duration;

public class Timestamp {
    private String timestampString; //"HH:MM:SS"
    private Duration duration;

    public Timestamp(String durString){
        this.timestampString = durString;
        this.duration = parseStringToDuration(durString);
    }

    public static Duration parseStringToDuration(String durString){
        String[] arr = durString.split(":");
        if(arr.length != 3) return null;

        long hours = Long.parseLong(arr[0]);
        long minutes = Long.parseLong(arr[1]);
        long seconds = Long.parseLong(arr[2]);

        return Duration.ofSeconds( (hours * 60 + minutes)* 60 + seconds);
    }
}
