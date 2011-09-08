package job;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import job.LinkedLists.List;
import job.LinkedLists.MutableList;
import job.LinkedLists.Node;

import static job.LinkedLists.*;

import org.junit.Test;

public class LinkedListsTests {

	/*
	 * Test the homebrew List basic functionality
	 */
	
	@Test
	public void testEmptyList() {
		List<Integer> empty = emptyList();
		assertTrue(empty.isEmpty());
		
		empty = listOf();
		assertTrue(empty.isEmpty());
	}
	
	@Test(expected = EmptyListException.class)
	public void testEmptyHead() {
		List<Integer> empty = emptyList();
		empty.head();
	}
	
	@Test(expected = EmptyListException.class)
	public void testEmptyTail() {
		List<Integer> empty = emptyList();
		empty.tail();
	}
	
	@Test
	public void testHomebrewList() {
		List<String> list = listOf("a");
		assertEquals("a", list.head());
		assertTrue(list.tail().isEmpty());
		
		list = listOf("a", "b", "c", "a", "d", "d", "d");
		assertEquals("a", list.head());
		assertEquals("b", list.tail().head());

		assertFalse(list.equals(emptyList()));
		assertFalse(emptyList().equals(list));
		
		assertEquals(list, listOf("a", "b", "c", "a", "d", "d", "d"));
		assertFalse(list.equals(listOf("a", "b", "c", "a", "d", "d")));
		assertEquals(list, list("a", listOf("b", "c", "a", "d", "d", "d")));
		
		assertEquals(listOf("a", "b", "c"), reverse(listOf("c", "b", "a")));
	}
	
	/*
	 * Using homebrew List class
	 */

	@Test
	public void testRmDuplicates() {
		List<String> before = listOf("a", "b", "c", "a", "d", "d", "d");
		List<String> expected = listOf("d", "c", "b", "a");
		
		List<String> result = rmDuplicates(before);
		assertEquals(expected.size(), result.size());
		assertTrue(expected.equals(result));
		assertEquals(expected, result);
	}
	
	@Test
	public void testNthToLast() {
		List<String> list = listOf("a", "b");
		assertEquals("a", LinkedLists.nthToLast(list, 1));
		assertNull(nthToLast(list, 2));
		
		list = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m");
		assertEquals("h", nthToLast(list, 5));
	}
	
	@Test
	public void testAddition() {
		List<Integer> x = listOf(3, 1, 5);  // 513
		List<Integer> y = listOf(5, 9, 2);  // 295
		assertEquals(Integer.valueOf(3), x.head());
		assertEquals(Integer.valueOf(5), y.head());
		
		List<Integer> expected = listOf(8, 0, 8);  // 808
		assertEquals(expected, add(x, y));
		
		List<Integer> differentLengths = listOf(7, 0, 1, 1);  //  1107
		assertEquals(differentLengths, add(listOf(9, 9, 9), listOf(9, 9), listOf(9)));
		assertEquals(differentLengths, add(listOf(9), listOf(9, 9), listOf(9, 9, 9)));
	}
	
	/*
	 * Using Java's LinkedList class 
	 */
	
	@Test
	public void testRemoveDuplicates() {
		LinkedList<Integer> base = initBase();		
		LinkedList<Integer> expected = initExpected();
		
		LinkedList<Integer> result = LinkedLists.removeDuplicates(base);
		
		assertEquals(7, base.size());
		assertEquals(expected.size(), result.size());
		assertEquals(expected, result);
	}

	private LinkedList<Integer> initExpected() {
		LinkedList<Integer> expected = new LinkedList<Integer>();
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		return expected;
	}

	private LinkedList<Integer> initBase() {
		LinkedList<Integer> base = initExpected();
		base.add(1);
		base.add(4);
		base.add(4);
		return base;
	}
	
	@Test
	public void testNthToLastJava() {
		String[] seed = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
		LinkedList<String> list = new LinkedList<String>(Arrays.asList(seed));
		assertEquals("h", list.get(list.size() - 5 - 1));
	}
	
	/*
	 * Using MutableList
	 */
	
	@Test
	public void testRemoveDuplicatesWithoutBuffer() {
		MutableList<String> base = new MutableList<String>("a");
		base.append("b").append("c").append("a").append("d").append("d");
		
		MutableList<String> expected = new MutableList<String>("a");
		expected.append("b").append("c").append("d");
		
		base.removeDuplicatesWithoutBuffer();
		
		assertEquals(expected, base);		
	}
	
	@Test
	public void testRemoveOnlyKnowingNode() {
		MutableList<String> base = new MutableList<String>("a").append("b").append("c");
		MutableList<String> expected = new MutableList<String>("a").append("c");
		Node<String> toRemove = base.next();
		
		toRemove.remove();
		assertEquals(expected, base);
	}
	
	@Test
	public void testCircularList() {
		MutableList<String> list = new MutableList<String>("a").append("b").append("c").append("d").append("e");
		Node<String> nodeC = list.next().next;
		Node<String> nodeE = list.head();
		while (!nodeE.value.equals("e"))
			nodeE = nodeE.next;
		nodeE.next = nodeC;
		
		assertEquals(nodeC, LinkedLists.circularReference(list));
	}
}
