import java.util.Map;
import java.util.regex.Pattern;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class WordCount {

    private static final Pattern ON_WHITESPACE = Pattern.compile("\\p{javaWhitespace}+");

    public Map<String, Integer> phrase(String phrase) {
        return ON_WHITESPACE.splitAsStream(phrase.replaceAll("\\p{Punct}", "")
                                                 .toLowerCase())
                            .collect(toMap(identity(), w -> 1, Integer::sum));
    }
}