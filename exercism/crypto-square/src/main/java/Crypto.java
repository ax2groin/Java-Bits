import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public final class Crypto {

    private static final Function<List<Character>, String> CHARS_TO_STRING
            = chars -> chars.stream().map(Object::toString).reduce((acc, e) -> acc + e).orElse("");

    // TODO: Simple way to make this generic without making separate class?
    private static final Collector<Map.Entry<Integer, Character>, ?, Map<Integer, List<Character>>> MULTI_MAP_COLLECTOR
            = groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, toList()));

    private final String string;

    public Crypto(String string) {
        this.string = string.replaceAll("\\P{Alnum}", "").toLowerCase();
    }

    public String getNormalizedPlaintext() { return string; }

    public int getSquareSize() { return (int) Math.ceil(Math.sqrt(string.length())); }

    public List<String> getPlaintextSegments() {
        int size = getSquareSize();
        return Arrays.asList(string.split("(?<=\\G.{" + size + "})"));
    }

    public String getCipherText() {
        return String.join("", toCipherText());
    }

    public String getNormalizedCipherText() {
        return String.join(" ", toCipherText());
    }

    private static Map<Integer, Character> mapIndexed(String str) {
        Map<Integer, Character> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++)
            map.put(i, str.charAt(i));
        return map;
    }

    private List<String> toCipherText() {
        Map<Integer, List<Character>> indexed
                = getPlaintextSegments().stream()
                                        .map(Crypto::mapIndexed)
                                        .map(Map::entrySet)
                                        .flatMap(Collection::stream)
                                        .collect(MULTI_MAP_COLLECTOR);

        return indexed.values().stream().map(CHARS_TO_STRING).collect(toList());
    }
}