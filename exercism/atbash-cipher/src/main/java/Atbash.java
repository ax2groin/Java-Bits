import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public final class Atbash {

    public static String encode(String input) {
        return String.join(" ",
                input.toLowerCase()
                     .chars()
                     .mapToObj(Atbash::swap)
                     .collect(collectingAndThen(toList(), Atbash::concat))
                     .split("(?<=\\G.{5})"));
    }

    public static String decode(String input) {
        return input.chars()
                    .mapToObj(Atbash::swap)
                    .collect(collectingAndThen(toList(), Atbash::concat));
    }

    static Optional<Character> swap(int c) {
        if ('0' <= c && c <= '9')
            return of((char) c);
        if ('a' <= c && c < 'n')
            return of((char) ('z' - (c - 'a')));
        if ('m' < c && c <= 'z')
            return of((char) ('a' + 'z' - c));
        return empty();
    }

    static String concat(List<Optional<Character>> chars) {
        return chars.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(c -> c.toString())
                    .reduce((acc, e) -> acc + e)
                    .get();
    }
}