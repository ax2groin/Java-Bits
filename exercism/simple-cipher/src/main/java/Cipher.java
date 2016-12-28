import java.security.SecureRandom;

public final class Cipher {

    private static final String VALID_KEY = "^\\p{javaLowerCase}+$";

    private static final SecureRandom random = new SecureRandom();

    private final String key;

    public Cipher() { this.key = randomKey(); }

    public Cipher(String key) {
        if (!key.matches(VALID_KEY))
            throw new IllegalArgumentException("Invalid key sequence");

        this.key = key;
    }

    public String getKey() { return key; }

    public String encode(String string) {
        int len = Math.min(string.length(), key.length());
        char[] encoded = new char[len];
        for (int i = 0; i < len; i++)
            encoded[i] = normalize(string.charAt(i) + key.charAt(i) - 'a');
        return new String(encoded);
    }

    public String decode(String string) {
        int len = Math.min(string.length(), key.length());
        char[] decoded = new char[len];
        for (int i = 0; i < len; i++)
            decoded[i] = normalize(string.charAt(i) - key.charAt(i) + 'a');
        return new String(decoded);
    }

    private static char normalize(int c) {
        return (char) (((c - 'a' + 26) % 26) + 'a');
    }

    private static String randomKey() {
        char[] chars = new char[100];
        for (int i = 0; i < 100; i++)
            chars[i] = (char) (random.nextInt(26) + 'a');
        return new String(chars);
    }
}