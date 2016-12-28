import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class SumOfMultiples {

    public int Sum(int upTo, int[] multiples) {
        IntPredicate isMultiple = x ->
            IntStream.of(multiples)
                     .anyMatch(multiple -> x % multiple == 0);
        return IntStream.range(0, upTo)
                        .filter(isMultiple)
                        .sum();
    }
}
