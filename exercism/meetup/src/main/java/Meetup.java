import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static java.time.temporal.TemporalAdjusters.lastInMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * Solution solves for java.time instead of Joda Time.
 */
public final class Meetup {

    private final LocalDate startOfMonth;

    public Meetup(int month, int year) {
        startOfMonth = LocalDate.of(year, month, 1);
    }

    public LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        switch (schedule) {
            case FIRST:
                return startOfMonth.with(firstInMonth(dayOfWeek));
            case SECOND:
                return startOfMonth.plusDays(7).with(nextOrSame(dayOfWeek));
            case THIRD:
                return startOfMonth.plusDays(14).with(nextOrSame(dayOfWeek));
            case FOURTH:
                return startOfMonth.plusDays(21).with(nextOrSame(dayOfWeek));
            case TEENTH:
                return startOfMonth.plusDays(12).with(nextOrSame(dayOfWeek));
            case LAST:
                return startOfMonth.with(lastInMonth(dayOfWeek));
        }
        return LocalDate.now();
    }
}