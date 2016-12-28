import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Series {

    private static BiFunction<List<Integer>, Integer, Stream<List<Integer>>> partition =
            (list, length) -> IntStream.rangeClosed(0, list.size() - length)
                                       .mapToObj(l -> list.subList(l, l + length));

    private final String series;

    public Series(String series) { this.series = series; }

    public List<Integer> getDigits() {
        return series.chars()
                     .mapToObj(c -> c - '0')
                     .collect(Collectors.toList());
    }

    public List<List<Integer>> slices(int length) {
        if (length > series.length())
            throw new IllegalArgumentException();

        return partition.apply(getDigits(), length)
                        .collect(Collectors.toList());
    }
}