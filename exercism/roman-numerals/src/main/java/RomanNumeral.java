import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class RomanNumeral {

    private static final BiFunction<Integer, String, Function<Integer, Optional<String>>> UNIT
            = (modulus, ch) ->
            i -> {
                int n = (i / modulus) % 5;
                return (4 > n) ? of(repeat(ch, n)) : empty();
            };

    private static final BiFunction<Integer, String, Function<Integer, Optional<String>>> ABUT_DECI
            = (modulus, chs) -> i -> deciMod(i, modulus) == 9 ? of(chs) : empty();

    private static final BiFunction<Integer, String, Function<Integer, Optional<String>>> ABUT_SEMI_DECI
            = (modulus, chs) -> i -> deciMod(i, modulus) == 4 ? of(chs) : empty();

    private static final BiFunction<Integer, String, Function<Integer, Optional<String>>> SEMI_DECI
            = (modulus, ch) ->
            i -> {
                int n = deciMod(i, modulus);
                return (9 > n && n > 4) ? of(ch) : empty();
            };

    private static final List<Function<Integer, Optional<String>>> ROMANS = Arrays.asList(
            UNIT.apply(1000, "M"),
            ABUT_DECI.apply(1000, "CM"),
            SEMI_DECI.apply(1000, "D"),
            ABUT_SEMI_DECI.apply(1000, "CD"),
            UNIT.apply(100, "C"),
            ABUT_DECI.apply(100, "XC"),
            SEMI_DECI.apply(100, "L"),
            ABUT_SEMI_DECI.apply(100, "XL"),
            UNIT.apply(10, "X"),
            ABUT_DECI.apply(10, "IX"),
            SEMI_DECI.apply(10, "V"),
            ABUT_SEMI_DECI.apply(10, "IV"),
            UNIT.apply(1, "I"));

    private final int numeral;

    public RomanNumeral(int numeral) { this.numeral = numeral; }

    public String getRomanNumeral() {
        return ROMANS.stream()
                     .reduce("",
                             (acc, e) -> acc + e.apply(numeral).orElse(""),
                             (prev, acc) -> acc);
    }

    private static String repeat(String c, int times) {
        return new String(new char[times]).replace("\0", c);
    }

    private static int deciMod(int i, int modulus) {
        return i / (modulus / 10) % 10;
    }
}