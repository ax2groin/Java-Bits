package job;

import static org.junit.Assert.*;
import static stacks.StacksAndQueues.*;

import org.junit.Test;

import stacks.ArrayBackedStack;
import stacks.MinStack;
import stacks.MyQueue;
import stacks.SetOfStacks;
import stacks.TowersOfHanoi;

public class StacksAndQueuesTests {

	// 3.1
	@Test
	public void testArrayBackedStack() {
		ArrayBackedStack<String> base = new ArrayBackedStack<String>(9);
		Stack<String> stackOne = base.getStackOne();
		stackOne.push("A").push("B").push("C");
		assertEquals("C", stackOne.pop());
		
		stackOne.push("D");
		try {
			stackOne.push("E");
			fail("Expected StackFullException.");
		} catch (StackFullException sfe) {
			// expected
		}
		
		assertEquals("D", stackOne.pop());
		
		Stack<String> stackThree = base.getStackThree();
		stackThree.push("x").push("y").push("z");
		try {
			stackThree.push("-");
			fail("Expected StackFullException.");
		} catch (StackFullException sfe) {
			// expected
		}
		
		Stack<String> stackTwo = base.getStackTwo();
		stackTwo.push("a").push("b").push("c");
		try {
			stackTwo.push("d");
			fail("Expected StackFullException.");
		} catch (StackFullException sfe) {
			// expected
		}
		
		assertEquals("c", stackTwo.pop());
		assertEquals("b", stackTwo.pop());
		assertEquals("a", stackTwo.pop());
		assertEquals(null, stackTwo.pop());
		
		assertEquals("z", stackThree.pop());
		assertEquals("y", stackThree.pop());
		assertEquals("x", stackThree.pop());
		assertEquals(null, stackThree.pop());

		assertEquals("B", stackOne.pop());
		assertEquals("A", stackOne.pop());
		assertEquals(null, stackOne.pop());
	}
	
	// 3.2
	@Test
	public void testMinStack() {
		MinStack stack = new MinStack();
		stack.push(2).push(5).push(2);
		assertEquals(Integer.valueOf(2), stack.min());
		stack.pop();
		assertEquals(Integer.valueOf(2), stack.min());
		stack.push(1);
		assertEquals(Integer.valueOf(1), stack.min());
	}
	
	// 3.3
	@Test
	public void testSetOfStacks() {
		SetOfStacks<String> stackSet = new SetOfStacks<String>(3);
		for (char c = 'a'; c != 'z'; c++)
			stackSet.push(Character.toString(c));
		
		// Pull off 4 to ensure we've moves through one whole inner stack
		assertEquals("y", stackSet.pop());
		assertEquals("x", stackSet.pop());
		assertEquals("w", stackSet.pop());
		assertEquals("v", stackSet.pop());
		
		// Now check the inner stacks
		assertEquals("c", stackSet.popAt(0));
		assertEquals("f", stackSet.popAt(1));
		assertEquals("e", stackSet.popAt(1));
		assertEquals("d", stackSet.popAt(1));
		assertEquals("b", stackSet.popAt(1)); // first roll-back removes the stack
		assertEquals("i", stackSet.popAt(1)); // next call uses stack now at index
	}
	
	// 3.4
	@Test
	public void testTowersOfHanoi() {
		int n = 8;
		TowersOfHanoi game = new TowersOfHanoi(n);
		int moves = game.play();
		assertTrue(game.done());
		assertEquals((int) (Math.pow(2, n) - 1), moves);
	}
	
	// 3.5
	@Test
	public void TestTwoStackQueue() {
		MyQueue<String> queue = new MyQueue<String>();
		assertNull(queue.poll());
		queue.add("a").add("b").add("c");
		assertEquals("a", queue.peek());
		assertEquals("a", queue.poll());
		queue.add("d");
		assertEquals("b", queue.poll());
		assertEquals("c", queue.poll());
		assertEquals("d", queue.poll());
		assertNull(queue.poll());
	}
	
	// 3.6
	@Test
	public void testSort() {
		Stack<String> base = new MyStack<String>();
		base.push("a").push("f").push("b").push("e").push("e").push("c").push("d");
		Stack<String> expected = new MyStack<String>();
		expected.push("f").push("e").push("e").push("d").push("c").push("b").push("a");
		
		Stack<String> result = sort(base);
		// The equals check could return a false positive if one of the stacks is actually empty.
		assertNotNull(result.peek());
		assertNotNull(expected.peek());
		
		while (result.peek() != null && expected.peek() != null)
			assertEquals(expected.pop(), result.pop());
	}
}
