import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public final class SimpleLinkedList {

    private Node head;

    public SimpleLinkedList(Integer[] values) {
        this();
        Stream.of(values)
              .forEach(this::push);
    }

    public SimpleLinkedList() { }

    public int size() { return size(head); }

    private static int size(Node node) {
        return (node == null) ? 0 : 1 + size(node.next);
    }

    public int pop() {
        if (head == null)
            throw new NoSuchElementException();

        int value = head.value;
        head = head.next;
        return value;
    }

    public void push(int i) { head = new Node(i, head); }

    public void reverse() {
        Node node = head;
        head = null;
        for (; node != null; node = node.next)
            push(node.value);
    }

    public <T> T[] asArray(Class<T> clazz) {
        T[] ret = (T[]) Array.newInstance(clazz, size());
        Node node = head;
        for (int i = 0; node != null; node = node.next, i++)
            ret[i] = clazz.cast(node.value);
        return ret;
    }

    private static class Node {

        final int value;
        final Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}