import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class Strain {

    public static <T> List<T> keep(List<T> input, Predicate<T> filter) {
        return input.stream()
                    .reduce(list(),
                            (acc, e) -> filter.test(e) ? conjoin(acc, e) : acc,
                            (prev, acc) -> acc);
    }

    private static <T> List<T> list() { return new ArrayList<>(); }

    public static <T> List<T> discard(List<T> input, Predicate<T> filter) {
        return keep(input, filter.negate());
    }

    private static <T> List<T> conjoin(List<T> coll, T elem) {
        coll.add(elem);
        return coll;
    }
}