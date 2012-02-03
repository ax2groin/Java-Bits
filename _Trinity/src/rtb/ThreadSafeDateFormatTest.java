package rtb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static rtb.ThreadSafeDateFormat.getISO8601ExtendedFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

public class ThreadSafeDateFormatTest {

    private static final String[] dates = { "2012-02-02T19:03:00", "2000-01-01T00:00:01", "2011-10-22T05:42:13" };

    /**
     * Tests using a ThreadLocal to get DateFormat. Borrows directly from
     * http://www.codefutures.com/weblog/andygrove/2007/10/simpledateformat-and-thread-safety.html
     * but converted to work as a jUnit test.
     */
    @Test
    public void testGetISO8601ExtendedFormat() throws InterruptedException {
        TestThread run[] = new TestThread[dates.length];
        for (int i = 0; i < run.length; i++) {
            run[i] = new TestThread(i);
            run[i].start();
        }
        while (TestThread.done < 3)
            Thread.yield();
        assertEquals(3, TestThread.done);
        assertFalse(TestThread.failure);
    }
    
    @Test
    public void testXMLParsing() throws ParseException {
        // See that the parsing works with the XML library.
        // The DatatypeConverter is slower by about half.
        for (String date : dates) {
            Calendar xmlParse = DatatypeConverter.parseDateTime(date);
            Date dfParse =  getISO8601ExtendedFormat().parse(date);
            assertEquals(xmlParse.getTime(), dfParse);
        }
        
        // Milliseconds are optional, unlike with SimpleDateFormat.
        Calendar seconds = DatatypeConverter.parseDateTime("2012-02-02T19:03:00");
        Calendar millis = DatatypeConverter.parseDateTime("2012-02-02T19:03:00.000");
        assertEquals(seconds, millis);
        assertEquals(DatatypeConverter.printDateTime(seconds), DatatypeConverter.printDateTime(millis));
        
        // The time zone defaults to your current location, but can be set to anything.
        millis.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Calendar zoned = DatatypeConverter.parseDateTime("2012-02-02T19:03:00.000-08:00");
        assertEquals(DatatypeConverter.printDateTime(millis), DatatypeConverter.printDateTime(zoned));
    }

    private static class TestThread extends Thread {

        static boolean failure = false;
        static int done;

        private TestThread(final int i) {
            super(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        try {
                            String before = dates[i];
                            Date date = getISO8601ExtendedFormat().parse(before);
                            String after = getISO8601ExtendedFormat().format(date);
                            if (!before.equals(after))
                                reportFailure();
                        } catch (Throwable e) {
                            // ParseException or NumberFormatException are possible
                            reportFailure();
                        }
                    }
                    done++;
                }
            });
        }

        protected static void reportFailure() {
            failure = true;
        }
    }
}