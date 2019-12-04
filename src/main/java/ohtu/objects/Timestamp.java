package ohtu.objects;

import javafx.util.Duration;

public class Timestamp implements Comparable<Timestamp> {
    //private int id;
    private String timestampString; //"HH:MM:SS"
    private Duration duration;

    public Timestamp(String durString){
        this.timestampString = durString;
        this.duration = stringToDuration(durString);
    }

    public Timestamp(Duration duration){
        this.duration = duration;
        this.timestampString = durationToString(duration);
    }

    @Override
    public String toString() {
        return timestampString;
    }

    public String getTimestampString() {
        return timestampString;
    }

    public Duration getDuration() {
        return duration;
    }

    public static Duration stringToDuration(String durString){
        String[] arr = durString.split(":");
        if(arr.length != 3) return null;

        long hours = Long.parseLong(arr[0]);
        long minutes = Long.parseLong(arr[1]);
        long seconds = Long.parseLong(arr[2]);

        return Duration.seconds( (hours * 60 + minutes)* 60 + seconds);
    }

    public static String durationToString(javafx.util.Duration duration) {
        if (duration == null)
            return "00:00:00";

        long seconds = (long) duration.toSeconds();
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }
    
    @Override
    public int compareTo(Timestamp timestamp) {
        return this.timestampString.compareTo(timestamp.getTimestampString());
    }
}
