package job.stacks;

/*
 * NOTE: I decided to break up the answers to section 3 questions, as they each were
 *       getting quite large.
 */
public final class StacksAndQueues {

    public static interface Stack<T> {
        public T pop();
        public T peek();
        public Stack<T> push(T item);
    }

    public static final class MyStack<T> implements Stack<T> {

        private Node<T> top;

        public T peek() {
            if (top == null)
                return null;
            return top.value;
        }

        public T pop() {
            if (top == null)
                return null;
            T val = top.value;
            top = top.next;
            return val;
        }

        public Stack<T> push(T item) {
            top = new Node<T>(item, top);
            return this;
        }

        public boolean isEmpty() {
            return peek() == null;
        }
    }

    // Package private
    static class SizedStack<T> implements Stack<T> {

        private Node<T> top;
        private int size;

        public T peek() {
            if (top == null)
                return null;
            return top.value;
        }

        public T pop() {
            if (top == null)
                return null;
            T val = top.value;
            top = top.next;
            size--;
            return val;
        }

        public Stack<T> push(T item) {
            top = new Node<T>(item, top);
            size++;
            return this;
        }

        public int size() {
            return size;
        }

    }

    // Package private for other classes to use, but not meant to be used
    // externally.
    static class Node<T> {

        protected final T value;
        protected final Node<T> next;

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    @SuppressWarnings("serial")
    public static class StackFullException extends RuntimeException {
    };

    /*
     * 3.6 Write a program to sort a stack in ascending order. You should not
     * make any assumptions about how the stack is implemented. The following
     * are the only functions that should be used to write this program: push |
     * pop | peek | isEmpty.
     * 
     * !! The way the question is written, I assume that the stack itself must
     * be modified.
     */
    public static <T extends Comparable<T>> Stack<T> sort(Stack<T> stack) {
        Stack<T> myStack = new MyStack<T>();
        while (stack.peek() != null) {
            T tmp = stack.pop();
            while (myStack.peek() != null && myStack.peek().compareTo(tmp) < 0)
                stack.push(myStack.pop());
            myStack.push(tmp);
        }
        return myStack;
    }
}