import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Bob {

    private static final Predicate<String> isEmpty = new Predicate<String>() {
        @Override
        public boolean test(String phrase) { return phrase.trim().isEmpty(); }

        @Override
        public String toString() { return "Fine. Be that way!"; }
    };

    private static final Predicate<String> isQuestion = new Predicate<String>() {
        @Override
        public boolean test(String phrase) { return phrase.endsWith("?"); }

        @Override
        public String toString() { return "Sure."; }
    };

    private static final Predicate<String> isShouting = new Predicate<String>() {
        @Override
        public boolean test(String phrase) {
            return phrase.equals(phrase.toUpperCase()) && phrase.matches(".*\\p{Alpha}.*");
        }

        @Override
        public String toString() { return "Whoa, chill out!"; }
    };

    private static final Supplier<Predicate<String>> whatever = () -> new Predicate<String>() {
        @Override
        public boolean test(String phrase) { return true; }

        @Override
        public String toString() { return "Whatever."; }
    };

    List<Predicate<String>> responses = Arrays.asList(isEmpty, isShouting, isQuestion);

    public String hey(String phrase) {
        return responses.stream()
                        .filter(pred -> pred.test(phrase))
                        .findFirst()
                        .orElseGet(whatever)
                        .toString();
    }
}