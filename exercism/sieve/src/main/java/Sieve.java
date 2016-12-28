import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public final class Sieve {

    private final int number;

    public Sieve(int number) { this.number = number; }

    public List<Integer> getPrimes() {
        return accumulatePrimes(new ArrayList<>(), IntStream.rangeClosed(2, number).toArray());
    }

    private static List<Integer> accumulatePrimes(List<Integer> primes, int[] candidates) {
        if (candidates.length == 0)
            return primes;

        int prime = candidates[0];
        primes.add(prime);

        int[] newCandidates = stream(copyOfRange(candidates, 1, candidates.length))
                .filter(i -> i % prime != 0)
                .toArray();
        return accumulatePrimes(primes, newCandidates);
    }
}