package job;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import job.recursion.Combinatorics;
import job.recursion.Fibonacci;
import job.recursion.GridTraverse;
import job.recursion.GridTraverse.Point;

import org.junit.Test;

public class RecursionTests {

    /*
     * 8.1 Write a method to generate the nth Fibonacci number.
     */
    @Test
    public void testFibonacci() {
        testLongs(0, Fibonacci.nth(0));
        testLongs(1, Fibonacci.nth(1));
        testLongs(1, Fibonacci.nth(2));
        testLongs(2, Fibonacci.nth(3));
        testLongs(3, Fibonacci.nth(4));
        testLongs(5, Fibonacci.nth(5));
        testLongs(12586269025L, Fibonacci.nth(50));
    }

    private void testLongs(long expected, long result) {
        assertEquals(Long.valueOf(expected), Long.valueOf(result));
    }

    /*
     * 8.2 Imagine a robot sitting on the upper left hand corner of an NxN grid.
     * The robot can only move in two directions: right and down. How many
     * possible paths are there for the robot? FOLLOW UP Imagine certain squares
     * are “off limits”, such that the robot cannot step on them. Design an
     * algorithm to get all possible paths for the robot.
     */
    @Test
    public void testPathCount() {
        testInts(2, GridTraverse.pathCount(1));
        testInts(6, GridTraverse.pathCount(2));
        testInts(20, GridTraverse.pathCount(3));
    }

    private void testInts(int expected, int result) {
        assertEquals(Integer.valueOf(expected), Integer.valueOf(result));
    }

    @Test
    public void testPathCountWithBlocked() {
        // Test some internals
        assertEquals(Point.at(2, 3), Point.at(2, 3));
        assertEquals(Point.at(2, 3), Point.at(2, 2).incY());
        assertEquals(Point.at(3, 2), Point.at(2, 2).incX());
        assertTrue(GridTraverse.isOutOfBounds(2, Point.at(2, 3)));
        assertTrue(GridTraverse.isOutOfBounds(2, Point.at(3, 2)));
        assertFalse(GridTraverse.isOutOfBounds(2, Point.at(2, 2)));

        // Test first without blockage
        testInts(GridTraverse.pathCount(3), GridTraverse.getPaths(3).size());

        // Now test with blockage
        List<GridTraverse.Point> blocked = new ArrayList<Point>();
        blocked.add(Point.at(2, 3));
        blocked.add(Point.at(5, 3));
        testInts(390, GridTraverse.pathCount(6, blocked));
    }

    /*
     * 8.3 Write a method that returns all subsets of a set.
     */
    @Test
    public void testCombinations() {
        // Testing this since it is the foundation of my answer to all subsets
        List<Integer> base = new ArrayList<Integer>();
        base.add(1);
        base.add(2);

        // n == 0 will return an empty set
        Set<List<Integer>> combinations = Combinatorics.combinations(base, 0);
        testInts(1, combinations.size());
        for (List<Integer> list : combinations)
            assertTrue(list.isEmpty());

        // n == 1 will return each element as a single-element list 
        combinations = Combinatorics.combinations(base, 1);
        testInts(2, combinations.size());
        for (List<Integer> list : combinations)
            assertTrue(list.contains(1) || list.contains(2));

        // n == 2 will return itself, since k == 2
        combinations = Combinatorics.combinations(base, 2);
        testInts(1, combinations.size());
        for (List<Integer> list : combinations) {
            assertTrue(list.contains(1));
            assertTrue(list.contains(2));
        }
    }

    @Test
    public void testSubset() {
        List<Integer> base = new ArrayList<Integer>();
        base.add(1);
        base.add(2);

        // Expected: [], [1], [2], [1,2]
        Set<List<Integer>> expected = new HashSet<List<Integer>>();
        expected.add(new ArrayList<Integer>()); // empty set
        List<Integer> justOne = new ArrayList<Integer>();
        justOne.add(1);
        expected.add(justOne);
        List<Integer> justTwo = new ArrayList<Integer>();
        justTwo.add(2);
        expected.add(justTwo);
        expected.add(base);
        testInts(4, expected.size());

        Set<List<Integer>> subsets = Combinatorics.subsetsOf(base);
        assertEquals(expected, subsets);

        // Number of subsets is equal to 2^k, where k is the number of elements
        // to choose from.
        base.add(3);
        testInts((int) Math.pow(2, (double) base.size()),
                Combinatorics.subsetsOf(base).size());
        base.add(4);
        testInts((int) Math.pow(2, (double) base.size()),
                Combinatorics.subsetsOf(base).size());
        base.add(5);
        testInts((int) Math.pow(2, (double) base.size()),
                Combinatorics.subsetsOf(base).size());
    }
}
