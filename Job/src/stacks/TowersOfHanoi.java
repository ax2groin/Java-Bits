package stacks;

import stacks.StacksAndQueues.*;

/*
 * 3.4 In the classic problem of the Towers of Hanoi, you have 3 rods and N disks
 *     of different sizes which can slide on to any tower. The puzzle starts with
 *     disks sorted in ascending order of size from top to bottom (e.g., each disk
 *     sits on top of an even larger one). You have the following constraints:
 *       (A) Only one disk can be moved at a time.
 *       (B) A disk is slid off the top of one rod onto the next rod.
 *       (C) A disk can only be placed on top of a larger disk.
 *     Write a program to move the disks from the first rod to the last using Stacks.
 */
public final class TowersOfHanoi {

	private final HanoiStack start = new HanoiStack();
	private final HanoiStack pivot = new HanoiStack();
	private final HanoiStack goal = new HanoiStack();
	
	private final HanoiStack[] rods = { start, pivot, goal };
	
	private final int disks;
	
	private int moves;
	
	public TowersOfHanoi(int n) {
		disks = n;
		for (int i = n; i > 0; i--)
			start.push(i);
	}

	public int play() {
		if (!done())
			shuffle(disks, 2, 0, 1);
		return moves;
	}
	
	public String state() {
		StringBuilder state = new StringBuilder();
		for (int i = 0; i < rods.length; i++) {
			state.append(i + " -> ");
			state.append(printStack(rods[i]));
			state.append("\n");
		}
		return state.toString();
	}
	
	private String printStack(HanoiStack stack) {
		StringBuilder visual = new StringBuilder("[");
		Node<Integer> run = stack.top;
		while (run != null) {
			visual.append(run.value);
			if (run.next != null)
				visual.append(",");
			run = run.next;
		}
		visual.append("]");
		return visual.toString();
	}
	
	public boolean done() {
		return rods[0].top == null && rods[1].top == null && rods[2].top.value == 1;
	}
	
	private void shuffle(int n, int to, int from, int p) {
		if (n > 0) {
			shuffle(n - 1, p, from, to);
			move(from, to);
			shuffle(n - 1, to, p, from);
		}
	}
	
	private void move(int from, int to) {
		rods[to].push(rods[from].pop());
		moves++;
	}
	
	@SuppressWarnings("serial")
	public static final class HanoiException extends RuntimeException {};
	
	private static final class HanoiStack implements Stack<Integer> {

		private Node<Integer> top;
		
		public Integer peek() {
			if (top == null)
				return null;
			return top.value;
		}
		
		public Integer pop() {
			if (top == null)
				return null;
			Integer val = top.value;
			top = top.next;
			return val;
		}

		public Stack<Integer> push(Integer item) {
			// Ensure that the rules are being followed.
			if (top != null && item > top.value)
				throw new HanoiException();
			top = new Node<Integer>(item, top);
			return this;
		}
		
	}
}