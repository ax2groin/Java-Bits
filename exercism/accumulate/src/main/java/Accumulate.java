import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

public final class Accumulate {

    public static <T> List<T> accumulate(List<T> input, UnaryOperator<T> func) {
        return accumulate(new ArrayList<>(), input.iterator(), func);
    }

    private static <T> List<T> accumulate(List<T> accumulator, Iterator<T> input, UnaryOperator<T> func) {
        if (input.hasNext()) {
            accumulator.add(func.apply(input.next()));
            return accumulate(accumulator, input, func);
        } else
            return accumulator;
    }
}