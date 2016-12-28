public class Hamming {

    public static int compute(String left, String right) {
        if (left.length() != right.length())
            throw new IllegalArgumentException("Sequences must be of the same length.");

        int count = 0;
        for (int i = 0; i < left.length(); i++)
            if (left.charAt(i) != right.charAt(i))
                count++;

        return count;
    }
}