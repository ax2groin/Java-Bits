import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class LargestSeriesProductCalculator {

    private static BiFunction<List<Long>, Integer, Stream<List<Long>>> partition =
            (list, length) -> IntStream.rangeClosed(0, list.size() - length)
                                       .mapToObj(l -> list.subList(l, l + length));

    private static Function<List<Long>, Long> product = (ls) -> ls.stream().reduce(1L, (a, b) -> a * b);

    private final List<Long> series;

    public LargestSeriesProductCalculator(String series) {
        if (isNull(series))
            throw new IllegalArgumentException("String to search must be non-null.");

        if (series.matches(".*\\P{Digit}.*"))
            throw new IllegalArgumentException("String to search may only contains digits.");

        this.series = series.chars().mapToObj(ch -> (long) ch - '0').collect(toList());
    }

    public long calculateLargestProductForSeriesLength(int length) {
        if (length < 0)
            throw new IllegalArgumentException("Series length must be non-negative.");

        if (length > series.size())
            throw new IllegalArgumentException("Series length must be less than or equal to the length of the string to search.");

        return partition.apply(series, length)
                        .map(product)
                        .max(Long::compare)
                        .orElse(0L);
    }
}
