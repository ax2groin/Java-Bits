package job;

import java.util.Arrays;

public final class ArraysAndStrings {

    /*
     * 1.1 Implement an algorithm to determine if a string has all unique
     * characters. What if you cannot use additional data structures?
     */
    public static boolean areCharsUnique(String input) {
        // Don't need to check last char and don't attempt invalid substring
        final int until = input.length() - 1;

        for (int i = 0; i < until; i++)
            if (input.substring(i + 1).indexOf(input.charAt(i)) != -1)
                return false;
        return true;
    }

    /*
     * 1.2 Write code to reverse a C-style String. (i.e., including the null
     * character)
     */
    public static char[] reverse(char[] input) {
        char[] rVal = new char[input.length];
        rVal[input.length - 1] = input[input.length - 1];
        int j = 0;
        for (int i = input.length - 2; i >= 0; i--)
            rVal[j++] = input[i];
        return rVal;
    }

    /*
     * 1.3 Design an algorithm and write code to remove the duplicate characters
     * in a string without using any additional buffer. NOTE: One or two
     * additional variables are fine. An extra copy of the array is not.
     * 
     * !! I say not allocating another buffer is impossible in Java due to the
     * nature of String.
     */
    public static String removeDuplicateChars(String input) {
        char[] charArray = input.toCharArray();
        int initLength = charArray.length;
        int shift = 0;
        for (int i = 0; i < initLength; i++) {
            if (charArray[i - shift] == 0)
                continue;
            if (first(charArray, charArray[i - shift]) != i - shift)
                extract(charArray, i - shift++);
        }
        return String.valueOf(charArray).trim();
    }

    // WARNING: This modifies the char[] passed in.
    private static void extract(char[] input, int pos) {
        while (pos < input.length) {
            if (pos < input.length - 1)
                input[pos] = input[pos + 1];
            else
                input[pos] = 0;
            pos++;
        }
    }

    private static int first(char[] input, char term) {
        for (int i = 0; i < input.length; i++)
            if (input[i] == term)
                return i;
        return -1;
    }

    /*
     * 1.4 Write a method to decide if two strings are anagrams or not.
     */
    public static boolean isAnagram(String base, String comp) {
        char[] cBase = base.toCharArray();
        char[] cComp = comp.toCharArray();
        Arrays.sort(cBase);
        Arrays.sort(cComp);
        return Arrays.equals(cBase, cComp);
    }

    /*
     * 1.5 Write a method to replace all spaces in a string with Ô%20Õ.
     */
    public static String percent20(String input) {
        return input.replaceAll(" ", "%20");
    }

    /*
     * 1.6 Given an image represented by an NxN matrix, where each pixel in the
     * image is 4 bytes, write a method to rotate the image by 90 degrees. Can
     * you do this in place?
     * 
     * WARNING: This modifies the matrix passed into it.
     */
    public static void rotate(int[][] matrix) {
        rotate(matrix, true);
    }

    public static void rotate(int[][] matrix, boolean clockwise) {
        for (int x = 0; x < matrix.length / 2; x++)
            for (int y = 0; y < matrix[x].length / 2; y++)
                if (clockwise)
                    rotateClockwise(matrix, x, y);
                else
                    rotateCounterclockwise(matrix, x, y);
    }

    private static void rotateClockwise(int[][] matrix, int x, int y) {
        int last = matrix.length - 1; // index of last element
        int tmp = matrix[x][y];
        matrix[x][y] = matrix[last - y][x];
        matrix[last - y][x] = matrix[last - x][last - y];
        matrix[last - x][last - y] = matrix[y][last - x];
        matrix[y][last - x] = tmp;
    }

    private static void rotateCounterclockwise(int[][] matrix, int x, int y) {
        int last = matrix.length - 1; // index of last element
        int tmp = matrix[x][y];
        matrix[x][y] = matrix[y][last - x];
        matrix[y][last - x] = matrix[last - x][last - y];
        matrix[last - x][last - y] = matrix[last - y][x];
        matrix[last - y][x] = tmp;
    }

    /*
     * 1.7 Write an algorithm such that if an element in an MxN matrix is 0, its
     * entire row and column is set to 0.
     * 
     * WARNING: This modifies the matrix passed into it.
     */
    public static void zeroMatrix(int[][] matrix) {
        int numVals = matrix.length * matrix[0].length;
        int[] xs = new int[numVals];
        int[] ys = new int[numVals];
        int i = 0;

        for (int x = 0; x < matrix.length; x++)
            for (int y = 0; y < matrix[x].length; y++)
                if (matrix[x][y] == 0) {
                    xs[i] = x;
                    ys[i++] = y;
                }

        for (int j = 0; j < i; j++)
            zeroRowAndColumn(matrix, xs[j], ys[j]);
    }

    private static void zeroRowAndColumn(int[][] matrix, int x, int y) {
        for (int m = 0; m < matrix.length; m++)
            for (int n = 0; n < matrix[x].length; n++)
                if (m == x || n == y)
                    matrix[m][n] = 0;
    }

    /*
     * 1.8 Assume you have a method isSubstring which checks if one word is a
     * substring of another. Given two strings, s1 and s2, write code to check
     * if s2 is a rotation of s1 using only one call to isSubstring (i.e.,
     * ÒwaterbottleÓ is a rotation of ÒerbottlewatÓ).
     */
    public static boolean isRotation(String base, String candidate) {
        if (base.length() == candidate.length())
            return isSubstring(base + base, candidate);
        return false;
    }

    public static boolean isSubstring(String base, String candidate) {
        return base.contains(candidate);
    }
}
