package job.recursion;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * 8.2 Imagine a robot sitting on the upper left hand corner of an NxN grid.
 *     The robot can only move in two directions: right and down. How many
 *     possible paths are there for the robot?
 *     FOLLOW UP
 *     Imagine certain squares are “off limits”, such that the robot cannot
 *     step on them. Design an algorithm to get all possible paths for the robot.
 */
public final class GridTraverse {

    public static final class Point {

        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Get a Point located at the indication position in the grid.
         * 
         * NOTE: We could do caching here to cut down on object creation. That
         * would also short-circuit the equals() method.
         * 
         * @param x
         *            X coordinate.
         * @param y
         *            Y coordinate.
         * 
         * @return A Point located at the indicated position.
         */
        public static Point at(int x, int y) {
            return new Point(x, y);
        }

        /**
         * Move up in the grid.
         * 
         * @return new Point with the y value increased by one.
         */
        public Point incY() {
            return new Point(x, y + 1);
        }

        /**
         * Move right in the grid.
         * 
         * @return new Point with x value increased by one.
         */
        public Point incX() {
            return new Point(x + 1, y);
        }

        @Override
        public int hashCode() {
            return 31 * (31 + x) + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;

            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }

    @SuppressWarnings("serial")
    public static final class Path extends CopyOnWriteArrayList<Point> {
    }

    /**
     * Count the number of paths between [0 0] and [n n].
     * 
     * NOTE: Because the number can be calculated mathematically, this method
     * does not actually traverse a grid.
     * 
     * @param n
     *            Size of the square grid.
     * 
     * @return Number of paths between [0 0] and [n n].
     */
    public static int pathCount(int n) {
        return factorial(n + n) / (factorial(n) * factorial(n));
    }

    /**
     * Count the number of paths between [0 0] and [n n] avoiding the traversal
     * of any points which are blocked.
     * 
     * @param n
     *            Size of the square grid.
     * @param blocked
     *            Collection of Points which are blocked, i.e., cannot be
     *            traversed.
     * 
     * @return Number of paths between [0 0] and [n n] that do not cross the
     *         blocked points.
     */
    public static int pathCount(int n, Collection<Point> blocked) {
        return getPaths(n, blocked).size();
    }

    /**
     * Get a collection of Paths that traverse a square grid from Point [0 0] to
     * a goal [n n].
     * 
     * @param n
     *            Size of the square grid.
     * 
     * @return A list of Paths that are open between [0 0] and [n n].
     */
    public static List<Path> getPaths(int n) {
        return getPaths(n, Collections.<Point> emptyList());
    }

    /**
     * Get a collection of Paths that traverse a square grid from Point [0 0] to
     * a goal [n n], and avoiding the traversal of any points which are blocked.
     * 
     * @param n
     *            Size of the square grid.
     * @param blocked
     *            Collection of Points which are blocked, i.e., cannot be
     *            traversed.
     * 
     * @return A list of Paths that are open between [0 0] and [n n].
     */
    public static List<Path> getPaths(int n, Collection<Point> blocked) {
        CopyOnWriteArrayList<Path> paths = new CopyOnWriteArrayList<Path>();
        getPaths(n, blocked, Point.at(0, 0), new Path(), paths);
        return paths;
    }

    /*
     * This recursive function takes advantage of CopyOnWriteArrayList to
     * bifurcate paths as they spread across the grid and add them to a final
     * collection as they are found.
     */
    private static void getPaths(int n, Collection<Point> blocked, Point point,
            Path path, List<Path> paths) {
        if (isOutOfBounds(n, point))
            return;
        if (blocked.contains(point))
            return;
        path.add(point);
        if (Point.at(n, n).equals(point)) {
            paths.add(path);
            return;
        }
        getPaths(n, blocked, point.incX(), path, paths);
        getPaths(n, blocked, point.incY(), path, paths);
    }

    /**
     * Check whether a point has passed beyond the bounds of an n-sized square
     * grid.
     * 
     * NOTE: Not checking for negative numbers because they are not created
     * internally.
     * 
     * @param n
     *            Size of the square grid.
     * @param point
     *            Point to check.
     * 
     * @return true if the point is located outside of the square grid.
     */
    public static boolean isOutOfBounds(int n, Point point) {
        return point.x > n || point.y > n;
    }

    /*
     * Recursive factorial method for internal use
     */
    private static int factorial(int n) {
        return factorial(n, 1);
    }

    private static int factorial(int n, int acc) {
        if (n == 0)
            return acc;
        return factorial(n - 1, acc * n);
    }

}
