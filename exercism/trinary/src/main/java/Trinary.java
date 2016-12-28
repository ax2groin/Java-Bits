public final class Trinary {

    private static final String VALID_DIGITS = "^[012]*$";

    public static int toDecimal(String number) {
        if (!number.matches(VALID_DIGITS))
            return 0;

        return reverse(number).chars()
                              .mapToObj(i -> (char) i)
                              .reduce(new Acc(3),
                                      (acc, e) -> (e != '0') ? acc.add(e) : acc.bump(),
                                      (prev, acc) -> acc)
                .sum;
    }

    private static class Acc {

        private final int incr;
        private int sum;
        private int base = 1;

        Acc(int incr) { this.incr = incr; }

        Acc add(char e) {
            sum += (base * Character.getNumericValue(e));
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