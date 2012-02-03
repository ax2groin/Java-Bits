package job.application;

/**
 * Two problems that were part of a timed test (one hour) through Codality.
 * Given more time, I may have answered them differently, but these were the
 * answers I submitted. For example, I don't like using the "back" variable this
 * way and would prefer to make {@code checkAgainstRear} a static method,
 * perhaps passing a tuple back to avoid shared state.
 * 
 * <p>
 * Variable names were dictated by the test, hence why they don't use Java-style
 * naming conventions.
 * 
 * @author Michael S. Daines
 */
public final class Codility {

    // Local variable to track last positive number checked.
    private int back;

    public int absDistinct(int[] A) {
        int count = 0;
        back = A.length - 1;

        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 0) {
                count++;
                continue;
            }

            // MIN_VALUE cannot be converted to a positive number as an int
            if (A[i] == Integer.MIN_VALUE) {
                count++;
                continue;
            }

            if (checkAgainstRear(A, Math.abs(A[i])))
                continue;
            count++;
        }
        return count;
    }

    private boolean checkAgainstRear(int[] array, int absolute) {
        while (array[back] > absolute)
            back--;
        return array[back] == absolute;
    }

    public int maxSliceSum(int[] A) {
        int max = Integer.MIN_VALUE;
        for (int P = 0; P < A.length; P++)
            for (int Q = P; Q < A.length; Q++)
                max = Math.max(max, sumValues(A, P, Q));
        return max;
    }

    private static int sumValues(int[] array, int from, int to) {
        int value = 0;
        for (int i = from; i < to; i++)
            value += array[i];
        return value;
    }
}