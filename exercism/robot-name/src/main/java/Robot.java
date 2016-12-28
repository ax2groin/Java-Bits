import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

public final class Robot {

    private static final SecureRandom random = new SecureRandom();

    private static final ConcurrentHashMap<String, Robot> robotRegistry = new ConcurrentHashMap<>();

    private String name;

    public Robot() { name = registerRobot(); }

    public String getName() { return name; }

    public void reset() { name = registerRobot(); }

    private String registerRobot() {
        String robotName;
        do {
            robotName = generateName();
        } while (robotRegistry.putIfAbsent(robotName, this) != null);
        return robotName;
    }

    private static char randomAlpha() { return (char) (random.nextInt(26) + 'A'); }

    private static char randomNumber() { return (char) (random.nextInt(10) + '0'); }

    private static String generateName() {
        return String.valueOf(new char[] { randomAlpha(), randomAlpha(), randomNumber(), randomNumber(), randomNumber() });
    }
}