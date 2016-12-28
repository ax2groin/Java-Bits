public final class Binary {

    private final String input;

    public Binary(String input) {
        this.input = (input.matches("^[01]*$")) ? reverse(input) : "";
    }

    public int getDecimal() {
        return input.chars()
                    .mapToObj(i -> (char) i)
                    .reduce(new BaseAcc(),
                            (acc, e) -> (e == '1') ? acc.add() : acc.bump(),
                            (prev, acc) -> acc)
                .sum;
    }

    private class BaseAcc {

        private int sum;
        private int base = 1;

        BaseAcc add() {
            sum += base;
            return bump();
        }

        BaseAcc bump() {
            base *= 2;
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