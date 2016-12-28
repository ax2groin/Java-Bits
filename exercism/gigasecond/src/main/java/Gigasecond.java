import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class Gigasecond {

    private static final long GIGASECOND = 1000000000;

    private LocalDateTime date;

    public Gigasecond(LocalDate date) { this(date.atStartOfDay()); }

    public Gigasecond(LocalDateTime time) {
        date = time.plus(GIGASECOND, ChronoUnit.SECONDS);
    }

    public LocalDateTime getDate() { return date; }
}