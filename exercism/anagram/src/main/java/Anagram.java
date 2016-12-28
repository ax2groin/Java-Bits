import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Anagram {

    private final Predicate<String> isAnagram;

    public Anagram(String master) {
        Predicate<String> notSameWord = word -> !master.equalsIgnoreCase(word);
        String sorted = anagramSort(master);
        this.isAnagram = notSameWord.and(word -> sorted.equals(anagramSort(word)));
    }

    public List<String> match(List<String> strings) {
        return strings.stream()
                      .filter(isAnagram)
                      .collect(Collectors.toList());
    }

    private static String anagramSort(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }
}