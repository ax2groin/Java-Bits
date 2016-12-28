import java.util.Locale;

public final class Pangrams {

    public static boolean isPangram(String phrase) {
        return 26 == phrase.toLowerCase(Locale.ENGLISH)
                           .replaceAll("\\P{Alpha}", "")
                           .chars()
                           .distinct()
                           .count();
    }
}