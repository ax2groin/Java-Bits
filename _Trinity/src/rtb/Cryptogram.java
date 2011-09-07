package rtb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Cryptogram {

    static final char[] ALPHABET = new char[] {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    static final List<Character> ALPHA_LIST = new ArrayList<Character>(ALPHABET.length);
    static {
        for (char letter : ALPHABET)
            ALPHA_LIST.add(letter);
    }

   private class Pair {

        private static final char EMPTY = '_';

        private final char crypt;
        private char letter = EMPTY;

        private Pair(char crypt) {
            this.crypt = crypt;
        }

        private void setLetter(char letter) {
            if (this.letter == EMPTY)
                this.letter = Character.toLowerCase(letter);
            else if (this.letter == letter)
                return;
            else
                throw new IllegalArgumentException("'" + crypt + "' is already set to '" + letter + "'.");
        }
    }

    private final HashMap<Character, Pair> cipher = new HashMap<Character, Pair>(ALPHABET.length);

    public Cryptogram() {
        for (int i = 0; i < ALPHABET.length; i++)
            cipher.put(ALPHABET[i], new Pair(ALPHABET[i]));
    }

    public void confirmPhrase(String encoded, String decoded) {
        if (encoded.length() != decoded.length())
            throw new IllegalArgumentException("String lengths do not match.");

        char[] sources = encoded.toCharArray();
        char[] values = decoded.toCharArray();
        for (int i = 0; i < sources.length; i++) {
            char source = Character.toLowerCase(sources[i]);
            if (ALPHA_LIST.contains(source))
                cipher.get(source).setLetter(Character.toLowerCase(values[i]));
        }
    }

    public String valueOf(String encoded) {
        char[] toDecode = encoded.toCharArray();
        char[] result = new char[toDecode.length];
        for (int i = 0; i < toDecode.length; i++) {
            Character letter = Character.toLowerCase(toDecode[i]);
            boolean toUpper = letter != toDecode[i];
            Pair pair = cipher.get(letter);
            if (pair == null)
                result[i] = letter;
            else
                result[i] = toUpper ? Character.toUpperCase(pair.letter) : pair.letter;
        }
        return new String(result);
    }

    public void guessLetter(Character inLetter, Character outLetter) {
        Character in = Character.toLowerCase(inLetter);
        Character out = Character.toLowerCase(outLetter);
        cipher.get(in).setLetter(out);
    }
}