import java.util.stream.IntStream;

public final class Luhn {

    private final int digits;

    public Luhn(int digits) { this.digits = digits; }

    public int getCheckDigit() { return digits % 10; }

    public int[] getAddends() { return toIntArray(digits); }

    public int getCheckSum() { return IntStream.of(getAddends()).sum(); }

    public boolean isValid() { return getCheckSum() % 10 == 0; }

    public static long create(int i) {
        long base = 10L * i;
        int checksum = (10 - IntStream.of(toIntArray(base)).sum() % 10) % 10;
        return base + checksum;
    }

    private static int lDouble(int value) {
        int ret = value * 2;
        return ret > 9 ? ret - 9 : ret;
    }

    private static int[] toIntArray(long number) {
        String str = Long.toString(number);
        int len = str.length();
        int offset = len % 2;
        int[] addends = new int[len];
        for (int i = 0; i < len; i++) {
            int value = Character.getNumericValue(str.charAt(i));
            addends[i] = (i % 2 == offset) ? lDouble(value) : value;
        }
        return addends;
    }
}