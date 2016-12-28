public final class Deque<T> {

    private Node<T> front;
    private Node<T> back;

    public void push(T value) {
        if (back == null)
            front = back = new Node<>(value);
        else
            back = Node.push(value, back);
    }

    public T pop() {
        T value = back.value;
        back = back.prev;
        return value;
    }

    public T shift() {
        T value = front.value;
        front = front.next;
        return value;
    }

    public void unshift(T value) {
        if (front == null)
            front = back = new Node<>(value);
        else
            front = Node.unshift(value, front);
    }

    private static final class Node<T> {

        private final T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T value) { this.value = value; }

        private static <T> Node<T> push(T value, Node<T> prev) {
            Node<T> node = new Node<>(value);
            node.prev = prev;
            prev.next = node;
            return node;
        }

        private static <T> Node<T> unshift(T value, Node<T> front) {
            Node<T> node = new Node<>(value);
            node.next = front;
            front.prev = node;
            return node;
        }
    }
}