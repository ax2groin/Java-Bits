public final class Octal {

    private static final String OCTAL_DIGITS = "^[0-7]+$";

    private final String number;

    public Octal(String number) { this.number = number; }

    public int getDecimal() {
        if (number.matches(OCTAL_DIGITS))
            return reverse(number).chars()
                                  .mapToObj(i -> (char) i)
                                  .reduce(new Acc(8),
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
            sum += (base * Character.getNumericValue(c));
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