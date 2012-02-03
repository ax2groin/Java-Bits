package rtb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ThreadSafeDateFormat {

    /** Fudged ISO 8601 Extended format. Does not include time zone. */
    public static final String ISO_8601_EXTENDED = "yyyy-MM-dd'T'HH:mm:ss";

    private static final ThreadLocal<DateFormat> EXTENDED_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(ISO_8601_EXTENDED);
        }
    };
    
    public static DateFormat getISO8601ExtendedFormat() {
        return EXTENDED_FORMAT.get();
    }
}
