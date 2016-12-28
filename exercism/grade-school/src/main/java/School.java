import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.binarySearch;

// Not Thread Safe
public final class School {

    private final Map<Integer, List<String>> db = new HashMap<>();

    public void add(String student, int level) {
        List<String> students = grade(level);
        int at = Math.max(0, binarySearch(students, student) - 1);
        students.add(at, student);
        db.putIfAbsent(level, students);
    }

    public Map<Integer, List<String>> db() { return db; }

    public List<String> grade(int level) {
        return db.getOrDefault(level, new ArrayList<>());
    }

    public Map<Integer, List<String>> sort() { return db; }
}