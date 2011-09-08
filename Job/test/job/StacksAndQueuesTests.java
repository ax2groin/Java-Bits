package job;

import static org.junit.Assert.*;
import static job.StacksAndQueues.*;

import org.junit.Test;

public class StacksAndQueuesTests {

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
}
