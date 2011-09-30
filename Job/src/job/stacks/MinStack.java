package job.stacks;

import job.stacks.StacksAndQueues.Node;
import job.stacks.StacksAndQueues.Stack;

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
public final class MinStack implements Stack<Integer> {

    private MinNode top;

    public Integer peek() {
        if (top == null)
            return null;
        return top.value;
    }

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