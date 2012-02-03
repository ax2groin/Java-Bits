package job.recursion;

/*
 * 8.1 Write a method to generate the nth Fibonacci number.
 */
public final class Fibonacci {

    private Fibonacci() {}

    public static long nth(int n) {
        return fib(0, 1, n);
    }

    private static long fib(long current, long next, int n) {
        if (n == 0)
            return current;
        return fib(next, (current + next), n - 1);
    }
}