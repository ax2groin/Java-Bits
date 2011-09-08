package job;

public final class StacksAndQueues {

	public static interface Stack<T> {
		public T pop();
		public Stack<T> push(T item);
	}
	
	@SuppressWarnings("serial")
	public static class StackFullException extends RuntimeException { };
	
	private static class Node<T> {
		
		protected final T value;
		protected final Node<T> next;
		
		private Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}
	
	/*
	 * 3.1 Describe how you could use a single array to implement three stacks.
	 */
	public static final class ArrayBackedStack<T> {
		
		private final Object[] storage;

		private InnerStack<T> stackOne;
		private InnerStack<T> stackTwo;
		private InnerStack<T> stackThree;
		
		public ArrayBackedStack(int size) {
			storage = new Object[size];
		}

		public Stack<T> getStackOne() {
			if (stackOne == null)
				stackOne = new InnerStack<T>(0, storage.length / 3);
			return stackOne;
		}

		public Stack<T> getStackTwo() {
			if (stackTwo == null)
				stackTwo = new InnerStack<T>(storage.length / 3, (storage.length / 3) * 2);
			return stackTwo;
		}

		public Stack<T> getStackThree() {
			if (stackThree == null)
				stackThree = new InnerStack<T>((storage.length / 3) * 2, storage.length);
			return stackThree;
		}
		
		private final class InnerStack<U> implements Stack<T> {
			
			private Node<T> top;
			private final Object lock = new Object();
			
			private final int base;
			private final int max;
			private int open;
			
			// start is inclusive, end is exclusive
			private InnerStack(int start, int end) {
				base = start;
				open = start;
				max = end;
			}
			
			public T pop() {
				if (top == null)
					return null;
				
				synchronized (lock) {
					Node<T> rNode = top;
					top = top.next == null ? null : top.next;
					if (open > base)
						storage[--open] = null;
					return rNode.value;
				}
			}
			
			public Stack<T> push(T item) {
				if (open >= max)
					throw new StackFullException();
				
				synchronized (lock) {
					top = new Node<T>(item, top);
					storage[open++] = top;
				}
				return this;
			}
			
		}
	}
	
	/*
	 * 3.2 How would you design a stack which, in addition to push and pop,
	 *     also has a function min which returns the minimum element? Push, pop
	 *     and min should all operate in O(1) time.
	 *     
	 *     !! NOTE: Because of the limitations of Java generics and boxing, it
	 *     seems you'd have to implement a different Stack for each type of Number.
	 *     
	 *     !! This approach also grows with each Node on the stack. You'd need
	 *     another stack for minimums to potentially cut down on this space usage. 
	 */
	public static final class MinStack implements Stack<Integer> {
		
		private MinNode top;
		
		public Integer pop() {
			MinNode rNode = top;
			if (top != null)
				top = (MinNode) top.next;
			return rNode.value;
		}

		public MinStack push(Integer item) {
			top = new MinNode(item, top);
			return this;
		}

		public Integer min() {
			return top.minimum;
		}
		
		private final class MinNode extends Node<Integer> {
			
			private final Integer minimum;
			
			private MinNode(Integer value, MinNode next) {
				super(value, next);
				
				if (next == null)
					minimum = value;
				else
					minimum = Math.min(value, next.minimum);
			}
		}
	}
	
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
}