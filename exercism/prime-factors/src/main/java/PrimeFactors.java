import java.util.ArrayList;
import java.util.List;

public final class PrimeFactors {

    public static List<Long> getForNumber(long input) {
        List<Long> acc = new ArrayList<>();

        while (input % 2 == 0) {
            acc.add(2L);
            input /= 2;
        }

        long factor = 3;
        while (input > 1) {
            if (0 == (input % factor)) {
                acc.add(factor);
                input /= factor;
            } else
                factor += 2;
        }
        return acc;
    }
}