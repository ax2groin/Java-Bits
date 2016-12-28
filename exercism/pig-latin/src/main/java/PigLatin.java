import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class PigLatin {

    private static final Pattern leadingConsonants = Pattern.compile("([^aeiou]*qu|[^aeiou]*)([aeiou].*)");

    public static String translate(String input) {
        return Stream.of(input.split("\\p{javaWhitespace}+"))
                     .map(PigLatin::wordToPigLatin)
                     .collect(joining(" "));
    }

    private static String wordToPigLatin(String word) {
        if (word.matches("^([aeiou]|yt|xr).*"))
            return word + "ay";

        Matcher matcher = leadingConsonants.matcher(word);
        if (matcher.matches())
            return matcher.group(2) + matcher.group(1) + "ay";
        else
            return word + "ay";
    }
}