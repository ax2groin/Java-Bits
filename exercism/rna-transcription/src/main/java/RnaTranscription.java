import static java.util.stream.Collectors.joining;

public final class RnaTranscription {

    public static String ofDna(String c) {
        return c.chars()
                .mapToObj(RnaTranscription::transcribe)
                .collect(joining(""));
    }

    private static String transcribe(int c) {
        switch (c) {
            case 'A': return "U";
            case 'C': return "G";
            case 'G': return "C";
            case 'T': return "A";
            default: return "";
        }
    }
}