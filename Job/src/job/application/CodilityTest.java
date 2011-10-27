package job.application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CodilityTest {

    @Test
    public void testAbsDistinct() {
        int[] base = new int[] { -5, -3, -2, -1, 0, 1, 3, 4, 5 };
        Codility test = new Codility();
        assertEquals(6, test.absDistinct(base));
    }

    @Test
    public void testMax() {
        int[] base = new int[] { 3, 2, -6, 4, 0 };
        Codility test = new Codility();
        assertEquals(5, test.maxSliceSum(base));
    }
}
