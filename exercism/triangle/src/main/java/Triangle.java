import java.util.Arrays;

public final class Triangle {

    private final int[] sides;

    public Triangle(int a, int b, int c) throws TriangleException {
        sides = new int[] { a, b, c };

        if (Arrays.stream(sides).anyMatch(side -> side <= 0))
            throw new TriangleException();

        Arrays.sort(sides);

        if (sides[0] + sides[1] <= sides[2])
            throw new TriangleException();
    }

    public Triangle(double a, double b, double c) throws TriangleException {
        this((int) (a * 10), (int) (b * 10), (int) (c * 10));
    }

    public TriangleKind getKind() {
        if (sides[1] == sides[2]) {
            if (sides[0] == sides[1])
                return TriangleKind.EQUILATERAL;

            return TriangleKind.ISOSCELES;
        }

        return TriangleKind.SCALENE;
    }
}