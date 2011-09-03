package job;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class ArraysAndStringsTests {

	@Test
	public void testUniqueCharacters() {
		assertTrue(ArraysAndStrings.areCharsUnique("abcdef"));
		
		assertFalse(ArraysAndStrings.areCharsUnique("abba"));
		
		// duplicate the last letter to makes sure my algorithm isn't skipping that
		assertFalse(ArraysAndStrings.areCharsUnique("abcdeff"));
	}

	@Test
	public void testStringReverse() {
		char[] forward = new char[] { 'f', 'o', 'r', 'w', 'a', 'r', 'd', 0 };
		char[] backward = new char[] { 'd', 'r', 'a', 'w', 'r', 'o', 'f', 0 };
		assertEquals(Arrays.toString(backward), Arrays.toString(ArraysAndStrings.reverse(forward)));
	}
	
	@Test
	public void testRemoveDuplicates() {
		assertEquals("abcdef", ArraysAndStrings.removeDuplicateChars("abcaddeff"));
	}
	
	@Test
	public void testAnagrams() {
		assertTrue(ArraysAndStrings.isAnagram("funeral", "realfun"));
	}
	
	@Test
	public void testPercent20() {
		assertEquals("a%20b%20", ArraysAndStrings.percent20("a b "));
	}
	
	@Test
	public void testMatrixRotation() {
		int[][] before = new int[][] {{ 0, 1, 1, 0},
		                              { 0, 0, 1, 1},
		                              { 0, 0, 0, 1},
		                              { 0, 0, 0, 0}};

		int[][] after = new int[][] {{ 0, 0, 0, 0},
		                             { 0, 0, 0, 1},
		                             { 0, 0, 1, 1},
		                             { 0, 1, 1, 0}};

		ArraysAndStrings.rotate(before);

		for (int i = 0; i < after.length; i++)
			for (int j = 0; j < after[i].length; j++)
				assertEquals("Comparing [" + i + ", " + j + "] --> " + after[i][j] + " vs. " + before[i][j], after[i][j], before[i][j]);
	}

	public static void printMatrix(int[][] matrix) {
		for (int x = 0; x < matrix.length; x++)
			System.out.println(Arrays.toString(matrix[x]));
		System.out.println();
	}
	
	@Test
	public void testZeroMatrix() {
		int[][] before = new int[][] {{ 0, 1, 1, 1},
                                      { 1, 1, 0, 1},
                                      { 1, 1, 1, 1}};

		int[][] after = new int[][] {{ 0, 0, 0, 0},
				                     { 0, 0, 0, 0},
				                     { 0, 1, 0, 1}};
		
		ArraysAndStrings.zeroMatrix(before);
		
		for (int i = 0; i < after.length; i++)
			assertTrue(Arrays.equals(after[i], before[i]));
	}
	
	@Test
	public void testRotation() {
		String s1 = "erbottlewat";
		String s2 = "waterbottle";
		assertTrue(ArraysAndStrings.isRotation(s1, s2));
	}
}
