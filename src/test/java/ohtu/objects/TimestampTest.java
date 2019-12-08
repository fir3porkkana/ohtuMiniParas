package ohtu.objects;

import org.junit.Assert;
import org.junit.Test;

import javafx.util.Duration;

public class TimestampTest {


    @Test
    public void TimestampStringIsGeneratedCorrectly(){
        Duration d = Duration.seconds(55);
        Timestamp t = new Timestamp(d);

        String s = "00:00:55";
        Assert.assertEquals(s, t.getTimestampString());
    }

    @Test
    public void TimestampDurationIsGeneratedCorrectly(){
        String s = "00:00:55";
        Timestamp t = new Timestamp(s);

        Duration d = Duration.seconds(55);

        Assert.assertEquals(d, t.getDuration());
    }

}
