import java.util.stream.IntStream;

public final class Difference {

    public static int computeSquareOfSumTo(int i) {
        int sum = IntStream.rangeClosed(1, i).sum();
        return sum * sum;
    }

    public static int computeSumOfSquaresTo(int i) {
        return IntStream.rangeClosed(1, i).map(x -> x * x).sum();
    }

    public static int betweenSquareOfSumAndSumOfSquaresTo(int i) {
        return computeSquareOfSumTo(i) - computeSumOfSquaresTo(i);
    }
}
