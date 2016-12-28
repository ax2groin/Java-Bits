import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Allergies {

    private final int code;

    public Allergies(int code) { this.code = code; }

    public boolean isAllergicTo(Allergen allergen) {
        return (allergen.getScore() & code) == allergen.getScore();
    }

    public List<Allergen> getList() {
        return Stream.of(Allergen.values())
                     .filter(this::isAllergicTo)
                     .collect(Collectors.toList());
    }
}