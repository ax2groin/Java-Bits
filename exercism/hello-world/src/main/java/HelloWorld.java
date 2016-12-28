import java.util.Objects;

public class HelloWorld {

    public static String hello(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            name = "World";

        return "Hello, " + name + '!';
    }
}