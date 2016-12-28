public final class Hexadecimal {

    private static final String HEX_DIGITS = "^\\p{XDigit}+$";

    public static int toDecimal(String number) {
        if (number.matches(HEX_DIGITS))
            return reverse(number).chars()
                                  .mapToObj(i -> (char) i)
                                  .reduce(new Acc(16),
                                          (acc, e) -> (e != '0') ? acc.add(e) : acc.bump(),
                                          (prev, acc) -> acc)
                    .sum;

        return 0;
    }

    private static class Acc {

        private final int incr;
        private int sum;
        private int base = 1;

        Acc(int incr) { this.incr = incr; }

        Acc add(char c) {
            int factor = ('1' <= c && c <= '9') ? Character.getNumericValue(c) : c - 'a' + 10;
            sum += (base * factor);
            return bump();
        }

        Acc bump() {
            base *= incr;
            return this;
        }
    }

    private static String reverse(String str) {
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length / 2; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[bytes.length - i - 1];
            bytes[bytes.length - i - 1] = temp;
        }
        return new String(bytes);
    }
}