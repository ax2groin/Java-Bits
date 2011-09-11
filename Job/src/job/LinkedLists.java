package job;

import java.util.Arrays;
import java.util.LinkedList;

public final class LinkedLists {

	public static interface List<T> {
		public T head();
		public List<T> tail();
		public boolean isEmpty();
		public boolean contains(T elem);
		public int size();
	}
	
	@SuppressWarnings("serial")
	public static class EmptyListException extends RuntimeException { };
	
	private static final List<? extends Object> EMPTY = new List<Object>() {

		public Object head() {
			throw new EmptyListException();
		}

		public List<Object> tail() {
			throw new EmptyListException();
		}

		public boolean isEmpty() {
			return true;
		}
		
		public boolean contains(Object elem) {
			return false;
		}
		
		public int size() {
			return 0;
		}
		
		@Override
		public String toString() {
			return "()";
		}
	};
	
	private static final class HomebrewList<T> implements List<T> {
		
		private final T _head;
		private final List<T> _tail;

		HomebrewList(T head, List<T> tail) {
			_head = head;
			_tail = tail;
		}
		
		public T head() {
			return _head;
		}

		public List<T> tail() {
			return _tail;
		}

		public boolean isEmpty() {
			return false;
		}
		
		public boolean contains(T elem) {
			return (head().equals(elem) || tail().contains(elem));
		}

		public int size() {
			return 1 + tail().size();
		}

		@Override
		public int hashCode() {
			return 31 * (head().hashCode() + tail().hashCode());
		}

		@Override
		public boolean equals(Object other) {
			if (other == null || other.getClass() != getClass())
				return false;
			final List<?> that = (List<?>) other;
			return head().equals(that.head()) && tail().equals(that.tail());
		}

		@Override
		public String toString() {
			return "(" + head() + " " + tail().toString() + ")";
		}
	}

	@SuppressWarnings("unchecked") // Not a risk
	public static <T> List<T> emptyList() {
		return (List<T>) EMPTY;
	}
	
	public static <T> List<T> list(T head, List<T> tail) {
		return new HomebrewList<T>(head, tail);
	}
	
	public static <T> List<T> listOf(T... elements) {
		if (elements.length == 0)
			return emptyList();
		return list(elements[0], listOf(Arrays.copyOfRange(elements, 1, elements.length)));
	}
	
	public static <T> List<T> reverse(List<T> list) {
		List<T> rList = emptyList();
		while (!list.isEmpty()) {
			rList = list(list.head(), rList);
			list = list.tail();
		}
		return rList;
	}

	/*
	 * 2.1 Write code to remove duplicates from an unsorted linked list.
	 *     FOLLOW UP
	 *     How would you solve this problem if a temporary buffer is not allowed?
	 *     
	 *     !! I say the follow up cannot be done with Java's LinkedList or my homebrew
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> rmDuplicates(List<T> base) {
		return rmDups(base, (List<T>) emptyList());
	}
	
	private static <T> List<T> rmDups(List<T> list, List<T> seed) {
		if (list.isEmpty())
			return seed;
		if (seed.contains(list.head()))
			return rmDups(list.tail(), seed);
		return rmDups(list.tail(), list(list.head(), seed));
	}
	
	public static <T> LinkedList<T> removeDuplicates(LinkedList<T> base) {
		return fRemove(new LinkedList<T>(base), new LinkedList<T>());
	}
	
	private static <T> LinkedList<T> fRemove(LinkedList<T> list, LinkedList<T> seed) {
		if (list == null || list.size() == 0)
			return seed;
		T node = list.pop();
		if (seed.contains(node))
			return fRemove(list, seed);
		seed.add(node);
		return fRemove(list, seed);
	}
	
	/*
	 * 2.2 Implement an algorithm to find the nth to last element of a singly linked list.
	 */
	public static <T> T nthToLast(List<T> list, int n) {
		if (list.isEmpty())
			return null;
		
		int size = n + 1;
		int count = 0;
		
		@SuppressWarnings("unchecked")
		T[] store = (T[]) new Object[size];
		
		while (!list.isEmpty()) {
			store[count % size] = list.head();
			list = list.tail();
			count++;
		}
		return store[count % size];
	}
	
	/*
	 * 2.4 You have two numbers represented by a linked list, where each node contains
	 *      a single digit. The digits are stored in reverse order, such that the 1’s digit
	 *      is at the head of the list. Write a function that adds the two numbers and
	 *      returns the sum as a linked list.
	 */
	public static List<Integer> add(List<Integer>... terms) {
		if (terms.length == 0)
			return listOf(0);
		
		List<Integer> sum = emptyList();
		int rem = 0;
		boolean[] done = new boolean[terms.length];
		while (true) {
			for (int i = 0; i < terms.length; i++) {
				if (terms[i].isEmpty()) {
					done[i] = true;
					continue;
				}
				rem += terms[i].head();
				terms[i] = terms[i].tail();
			}
			if (allDone(done) && rem == 0)
				return reverse(sum);  // Must reverse since result is created backwards.
			sum = list(rem % 10, sum);
			rem = rem / 10;
		}
	}
	
	private static boolean allDone(boolean[] check) {
		for (boolean val : check)
			if (!val)
				return false;
		return true;
	}
	
	/*
	 * All Mutable list code below here
	 */

	public static final class MutableList<T> {

		private Node<T> head;
		
		public MutableList() {
			head = null;
		}
		
		public MutableList(T element) {
			head = new Node<T>(element, null);
		}
		
		public MutableList(T element, MutableList<T> tail) {
			head = new Node<T>(element, tail.head);
		}
		
		public Node<T> head() {
			return head;
		}
		
		public Node<T> next() {
			return head.next;
		}

		public MutableList<T> append(T element) {
			if (head == null)
				head = new Node<T>(element, null);
			else {
				Node<T> tail = head;
				while (tail.next != null)
					tail = tail.next;
				tail.next = new Node<T>(element, null);
			}
			return this;
		}
		
		@Override
		public String toString() {
			StringBuilder rVal = new StringBuilder("MutableList[");
			Node<T> current = head;
			while (current != null) {
				rVal.append(current.value);
				if (current.next != null)
					rVal.append(", ");
				current = current.next;
			}
			rVal.append("]");
			return rVal.toString();
		}

		@Override
		public int hashCode() {
			if (head == null)
				return 31;
			return 31 * (head.value.hashCode() + next().hashCode());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			
			@SuppressWarnings("unchecked")
			MutableList<T> other = (MutableList<T>) obj;
			
			Node<T> node = head;
			Node<T> that = other.head;
			
			if (node == null) {
				if (that != null)
					return false;
				return true;
			}
			
			while (node != null && that != null) {
				if (!node.value.equals(that.value))
					return false;
				node = node.next;
				that = that.next;
			}
			
			if (node == null && that != null)
				return false;
			return true;
		}

		/*
		 * 2.1 Follow Up
		 */
		public void removeDuplicatesWithoutBuffer() {
			Node<T> node = head;
			while (node != null) {
				T current = node.value;
				Node<T> runner = head;
				while (runner != node) {
					if (runner.value.equals(current)) {
						remove(node);
						runner = node;
					}
					else
						runner = runner.next;
				}
				node = node.next;
			}
		}

		private void remove(Node<T> node) {
			if (node == head)
				head = head.next;
			else {
				Node<T> current = head;
				while (current != null) {
					if (current.next == node)
						current.next = node.next;
					current = current.next;
				}
			}
		}
	}
	

	public static final class Node<T> {

		protected T value;
		protected Node<T> next;
		
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
		
		@Override
		public String toString() {
			return "Node [value=" + value + ", next=" + (next == null ? null : next.value) + "]";
		}

		/*
		 * 2.3 Implement an algorithm to delete a node in the middle of a single linked list,
		 *     given only access to that node.
		 *     
		 *     !! This doesn't make sense with my homebrew or with Java's LinkedList
		 */
		public void remove() {
			if (next == null) // We have a problem!
				value = null;
			else {
				value = next.value;
				next = next.next;
			}
		}
	}

	/*
	 * 2.5 Given a circular linked list, implement an algorithm which returns node
	 *     at the beginning of the loop.
	 *     DEFINITION
	 *     Circular linked list: A (corrupt) linked list in which a node’s next
	 *     pointer points to an earlier node, so as to make a loop in the linked list.
	 *     
	 *     !! This requires that it is possible to create such a loop, i.e., mutable nodes.
	 */
	public static <T> Node<T> circularReference(MutableList<T> list) {
		Node<T> head = list.head();
		if (head.next == head)  // Loop of one
			return head;
		head = head.next;
		while (head.next != null) {
			Node<T> runner = list.head();
			while (runner != head) {
				if (runner.next == head.next)
					return head.next;
				runner = runner.next;
			}
			head = head.next;
		}
		return null;
	}
}
