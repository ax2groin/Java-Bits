import java.util.Arrays;

public final class PascalsTriangle {

    public static int[][] computeTriangle(int depth) {
        if (depth < 0)
            throw new IllegalArgumentException("No negative numbers, numbskull!");

        int[][] triangle = new int[depth][];
        int[] lastRow = new int[0];
        for (int i = 0; i < depth; i++) {
            triangle[i] = nextRow(lastRow);
            lastRow = triangle[i];
        }
        return triangle;
    }

    public static boolean isTriangle(int[][] triangle) {
        int[][] validTriangle = computeTriangle(triangle.length);
        for (int i = 0; i < triangle.length; i++)
            if (!Arrays.equals(triangle[i], validTriangle[i]))
                return false;

        return true;
    }

    private static int[] nextRow(int[] lastRow) {
        int[] row = new int[lastRow.length + 1];
        row[0] = row[lastRow.length] = 1;
        for (int i = 1; i < lastRow.length; i++)
            row[i] = lastRow[i - 1] + lastRow[i];
        return row;
    }
}