import java.util.function.Function;
import java.util.regex.Pattern;

public final class Acronym {

    /**
     * Split on space, punctuation, or a lowercase letter followed by
     * an uppercase letter.
     */
    private static final Pattern SPLIT
            = Pattern.compile("(\\p{Space}|\\p{Punct}|\\p{Lower}(?=\\p{Upper}))+");

    private static final Function<String, Character> firstLetter
            = word -> word.charAt(0);

    private static final Function<String, String> toInitials
            = firstLetter.andThen(Character::toUpperCase)
                         .andThen(Object::toString);

    public static String generate(String phrase) {
        return SPLIT.splitAsStream(phrase)
                    .map(toInitials)
                    .reduce((acc, e) -> acc + e)
                    .orElseGet(() -> "");
    }
}
