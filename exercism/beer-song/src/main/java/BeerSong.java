import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class BeerSong {

    public static String verse(int n) {
        String bottleCount = bottles(n);
        return uppercaseFirstLetter(bottleCount) + " of beer on the wall, "
                + bottleCount + " of beer.\n"
                + action(n)
                + ", " + bottles(n - 1) + " of beer on the wall.\n\n";
    }

    public static String sing(int from, int to) {
        return IntStream.rangeClosed(to, from)
                        .map(i -> to - i + from)
                        .mapToObj(BeerSong::verse)
                        .collect(joining(""));
    }

    public static String singSong() {
        return sing(99, 0);
    }

    private static String bottles(int n) {
        switch (n) {
            case 0: return "no more bottles";
            case 1: return "1 bottle";
            case -1: return "99 bottles";
            default: return n + " bottles";
        }
    }

    private static String uppercaseFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String action(int n) {
        switch (n) {
            case 0: return "Go to the store and buy some more";
            case 1: return "Take it down and pass it around";
            default: return "Take one down and pass it around";
        }
    }
}