package job.application;

import static job.application.XOR.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.Test;

public class XORTest {

    @Test
    public void testNodeXOR() {
        assertEquals(0, memoizedXOR(null, new HashMap<Node, Integer>()));
        
        assertEquals(0, memoizedXOR(new MyNode(), new HashMap<Node, Integer>()));
        
        assertEquals(-1 ^ 0 ^ 1 ^ 2 ^ 3 ^ 4, memoizedXOR(getSimpleTree(), new HashMap<Node, Integer>()));
        
        int deeperExpectedXOR = 7 ^ 42 ^ Integer.MAX_VALUE ^ -655321 ^ -1 ^ Integer.MIN_VALUE ^ 33 ^ 45 ^ -24;
        assertEquals(deeperExpectedXOR, memoizedXOR(getDeeperTree(), new HashMap<Node, Integer>()));
    }
    
    @Test
    public void testGetLargest() {
        assertNull(findSubtreeWithLargestXor(null));
        
        MyNode deadHead = new MyNode();
        assertEquals(deadHead, findSubtreeWithLargestXor(deadHead));
        
        MyNode tree = getSimpleTree();
        assertEquals(tree.children[4], findSubtreeWithLargestXor(tree));
        
        tree = getDeeperTree();
        assertEquals(tree.children[1], findSubtreeWithLargestXor(tree));
        
        Node thirdChild = tree.children[2];
        assertEquals(thirdChild, findSubtreeWithLargestXor(thirdChild));
        
        Node grandchild = thirdChild.getChildren()[2];
        Node expected = grandchild.getChildren()[0];
        assertEquals(expected, findSubtreeWithLargestXor(grandchild));
    }
    
    @Test
    public void testCircularReference() {
        MyNode root = new MyNode(4);
        MyNode child = new MyNode(7);
        root.children = new MyNode[] { child };
        child.children = new MyNode[] { root };

        assertEquals(4 ^ 7, memoizedXOR(root, new HashMap<Node, Integer>()));
        assertEquals(7 ^ 4, memoizedXOR(child, new HashMap<Node, Integer>()));
        
        assertEquals(child, findSubtreeWithLargestXor(root));
        assertEquals(root, findSubtreeWithLargestXor(child));
    }

    private MyNode getSimpleTree() {
        MyNode allMyChildren = new MyNode(-1);
        allMyChildren.children = new MyNode[5];
        for (int i = 0; i < allMyChildren.children.length; i++)
            allMyChildren.children[i] = new MyNode(i);
        return allMyChildren;
    }
    
    /*
     * Produces Tree structure with the following values/XORs:
     * 
     *       +---------7 (655342)---------------------+
     *      ||           ||                           ||  
     *  42 (42)  2_147_483_647 (Integer.MAX_VALUE)    ||
     *              +----------------------------  -655321 (2_146_828_348)
     *              ||          ||                           ||    
     *             -1 (-1)  -2147483648 (Integer.MIN_VALUE)  ||
     *                                                  33 (-28)
     *                                        45 (45) ---+--- -24 (-24)
     */
    private MyNode getDeeperTree() {
        MyNode[] greatGrandchildren = new MyNode[] { new MyNode(45), new MyNode(-24) };
        
        MyNode grandchild = new MyNode(33);
        grandchild.children = greatGrandchildren;
        MyNode[] grandchildren = new MyNode[] { new MyNode(-1), new MyNode(Integer.MIN_VALUE), grandchild };
        
        MyNode child = new MyNode(-655321);
        child.children = grandchildren;
        MyNode deadhead = new MyNode(Integer.MAX_VALUE);
        deadhead.children = new MyNode[5];
        
        MyNode root = new MyNode(7);
        root.children = new MyNode[] { new MyNode(42), deadhead, child };
        
        return root;
    }

    private final class MyNode implements Node {

        private int value;
        private Node[] children;
        
        public MyNode() { }
        
        public MyNode(int value) {
            this.value = value;
        }

        public Node[] getChildren() {
            return children;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "MyNode [value=" + value + ", xor=" + memoizedXOR(this, new HashMap<Node, Integer>()) + "]";
        }
    }
}
