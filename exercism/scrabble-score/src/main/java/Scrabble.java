import static java.util.Optional.ofNullable;

public final class Scrabble {

    private final String word;

    public Scrabble(String word) {
        this.word = ofNullable(word).orElse("");
    }

    public int getScore() {
        return word.chars()
                   .map(Scrabble::scoreLetter)
                   .sum();
    }

    private static int scoreLetter(int i) {
        char letter = (char) i;
        if (Character.isAlphabetic(letter))
            switch (Character.toUpperCase(letter)) {
                case 'D': case 'G':
                    return 2;
                case 'B': case 'C': case 'M': case 'P':
                    return 3;
                case 'F': case 'H': case 'V': case 'W': case 'Y':
                    return 4;
                case 'K':
                    return 5;
                case 'J': case 'X':
                    return 8;
                case 'Q': case 'Z':
                    return 10;
                default:
                    return 1;
            }

        return 0;
    }
}