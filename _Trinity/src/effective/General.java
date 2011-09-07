package effective;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class General {

    // Don't use float/double for numbers needing exact values
    // Effective Java, 218
    public static final BigDecimal TEN_CENTS = new BigDecimal(".10");

    // The right way to use varargs to pass one or more arguments
    // Effective Java, 198
    public static int min(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs)
            if (arg < min)
                min = arg;
        return min;
    }

    // Best idiom for printing an array.
    // Effective Java, 199
    public static <T> String printArray(T[] myArray) {
        return Arrays.toString(myArray);
    }

    // Idiom for converting varargs to a List, missing from API
    // Effective Java, 199
    public static <T> List<T> gather(T... args) {
        return Arrays.asList(args);
    }

    // The right way to return an array from a collection
    // Effective Java, 202
    private final List<Object> validObjects = new ArrayList<Object>();

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public Object[] getObjects() {
        return validObjects.toArray(EMPTY_OBJECT_ARRAY);
    }

    // The right way to return a copy of a collection
    public List<Object> getObjectList() {
        if (validObjects.isEmpty())
            return Collections.emptyList(); // Always returns same list
        else
            return new ArrayList<Object>(validObjects);
    }

    public int getRandomNumber() {
        // Best idiom for returning a random number.
        // Don't use nextInt() [without an argument]
        // Effective Java, 216
        // Here's d20:
        Random d20 = new Random();
        return d20.nextInt(20);
    }
}