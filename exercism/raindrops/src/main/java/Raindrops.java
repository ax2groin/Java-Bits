import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Raindrops {

    enum Drop {

        FACTOR_OF_3(3, "Pling"),
        FACTOR_OF_5(5, "Plang"),
        FACTOR_OF_7(7, "Plong");

        private final Predicate<Integer> pred;
        private final String sound;

        Drop(int n, String sound) {
            this.pred = factorOf(n);
            this.sound = sound;
        }

        String sound() { return sound; }

        private static Predicate<Integer> factorOf(int n) {
            return i -> 0 == i % n;
        }
    }

    public static String convert(int input) {
        return Stream.of(Drop.values())
                     .filter(drop -> drop.pred.test(input))
                     .map(Drop::sound)
                     .reduce(String::concat)
                     .orElse(Integer.toString(input));
    }
}