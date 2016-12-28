import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class DNA {

    private static final List<Character> VALID = Arrays.asList('A', 'C', 'G', 'T');

    private final String sequence;

    public DNA(String sequence) { this.sequence = sequence; }

    public long count(char a) {
        if (!VALID.contains(a))
            throw new IllegalArgumentException("Must pass a valid nucleobase.");

        return sequence.chars().filter(c -> c == a).count();
    }

    public Map<Character, Integer> nucleotideCounts() {
        return sequence.chars()
                       .mapToObj(i -> (char) i)
                       .collect(Collectors.toMap(identity(), c -> 1, Integer::sum, DNA::baseCount));
    }

    private static Map<Character, Integer> baseCount() {
        HashMap<Character, Integer> base = new HashMap<>();
        base.put('A', 0);
        base.put('C', 0);
        base.put('G', 0);
        base.put('T', 0);
        return base;
    }
}