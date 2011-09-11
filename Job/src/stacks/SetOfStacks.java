package stacks;

import static stacks.StacksAndQueues.*;

import java.util.ArrayList;

import stacks.StacksAndQueues.Stack;

/*
 * 3.3 Imagine a (literal) stack of plates. If the stack gets too high, it might
 *     topple. Therefore, in real life, we would likely start a new stack when
 *     the previous stack exceeds some threshold. Implement a data structure
 *     SetOfStacks that mimics this. SetOfStacks should be composed of several
 *     stacks, and should create a new stack once the previous one exceeds
 *     capacity. SetOfStacks.push() and SetOfStacks.pop() should behave
 *     identically to a single stack (that is, pop() should return the same values
 *     as it would if there were just a single stack).
 *     
 *     FOLLOW UP
 *     Implement a function popAt(int index) which performs a pop operation on a
 *     specific sub-stack.
 */
public final class SetOfStacks<T> implements Stack<T> {

	private final int maxStackSize;
	private final ArrayList<SizedStack<T>> stackSet = new ArrayList<SizedStack<T>>();

	public SetOfStacks(int size) {
		maxStackSize = size;
	}
	
	public T peek() {
		return last().peek();
	}
	
	public T pop() {
		Stack<T> tail = last();
		T val = tail.pop();
		while (val == null && !stackSet.isEmpty()) {
			stackSet.remove(tail);
			tail = last();
			val = tail.pop();
		}
		return val;
	}

	public T popAt(int index) {
		if (index >= stackSet.size())
			throw new IndexOutOfBoundsException();
		
		Stack<T> stack = stackSet.get(index);
		T val = stack.pop();
		while (val == null && index > 0 && !stackSet.isEmpty()) {
			stackSet.remove(stack);
			stack = stackSet.get(--index);
			val = stack.pop();
		}
		return val;
	}

	public Stack<T> push(T item) {
		SizedStack<T> last = last();
		if (last.size() >= maxStackSize) {
			last = new SizedStack<T>();
			last.push(item);
			stackSet.add(last);
		}
		else
			last.push(item);
		return last;
	}
	
	private SizedStack<T> last() {
		if (stackSet.isEmpty()) {
			SizedStack<T> tail = new SizedStack<T>();
			stackSet.add(tail);
			return tail;
		}
		return stackSet.get(stackSet.size() - 1);
	}
		
}
