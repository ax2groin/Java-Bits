package job.stacks;

import job.stacks.StacksAndQueues.Node;
import job.stacks.StacksAndQueues.Stack;
import job.stacks.StacksAndQueues.StackFullException;

/*
 * 3.1 Describe how you could use a single array to implement three stacks.
 */
public final class ArrayBackedStack<T> {

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
            stackTwo = new InnerStack<T>(storage.length / 3,
                    (storage.length / 3) * 2);
        return stackTwo;
    }

    public Stack<T> getStackThree() {
        if (stackThree == null)
            stackThree = new InnerStack<T>((storage.length / 3) * 2,
                    storage.length);
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

        public T peek() {
            if (top == null)
                return null;
            return top.value;
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
