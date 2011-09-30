package job.stacks;

import job.stacks.StacksAndQueues.Node;
import job.stacks.StacksAndQueues.Stack;

/*
 * 3.5 Implement a MyQueue class which implements a queue using two stacks.
 */
public final class MyQueue<T> {

    private Stack<T> out;
    private InnerStack<T> in = new InnerStack<T>();

    public MyQueue<T> add(T item) {
        in.push(item);
        return this;
    }

    public T peek() {
        if (out.peek() == null)
            swapInToOut();
        return out.peek();
    }

    public T poll() {
        if (out == null || out.peek() == null)
            swapInToOut();
        return out.pop();
    }

    private void swapInToOut() {
        out = InnerStack.reverse(in);
        in = new InnerStack<T>();
    }

    private static final class InnerStack<T> implements Stack<T> {

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

        private static <E> Stack<E> reverse(InnerStack<E> stack) {
            Stack<E> reversed = new InnerStack<E>();
            Node<E> node = stack.top;
            while (node != null) {
                reversed.push(node.value);
                node = node.next;
            }
            return reversed;
        }
    }
}
