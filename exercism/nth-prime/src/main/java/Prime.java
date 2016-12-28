import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class Prime {

    private static final ArrayList<Integer> primes
            = new ArrayList<Integer>() {{ add(2); add(3); }};

    public static int nth(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Illegal prime.");

        while (primes.size() < n)
            nextPrime(primes);

        return primes.get(n - 1);
    }

    private static boolean isPrime(int n) {
        return primes.stream()
                     .noneMatch(i -> n % i == 0);
    }

    private static void nextPrime(List<Integer> primes) {
        primes.add(IntStream.iterate(primes.get(primes.size() - 1) + 2, i -> i + 2)
                            .filter(Prime::isPrime)
                            .findFirst()
                            .orElse(0));
    }
}