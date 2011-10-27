package job.application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael S. Daines
 */
public final class XOR {

    public static Node findSubtreeWithLargestXor(Node tree) {
        if (null == tree)
            return null;

        if (!hasChildren(tree))
            return tree;

        return protectedFind(tree, new HashSet<Node>(), new HashMap<Node, Integer>());
    }

    /**
     * Find the largest XOR subtree while protecting against possible circular
     * references.
     * 
     * @param root
     *            Root node of the tree to inspect.
     * @param visited
     *            A {@code Set} to track all visited {@code Node}s.
     * @param xorCache
     *            A {@code Map} used to memoize XOR calculations, which will
     *            improve performance on trees of any significant size.
     * 
     * @return The subtree with the largest XOR value. Note that in the case of
     *         circular references, the source of the circular reference
     *         (relative to the root) will have a shunted child and return an
     *         XOR exclusive of the circular reference.
     */
    private static Node protectedFind(Node root, Set<Node> visited, Map<Node, Integer> xorCache) {
        if (null == root || visited.contains(root))
            return null;

        visited.add(root);

        if (!hasChildren(root))
            return root;

        Node largest = root;
        int max = memoizedXOR(root, xorCache);

        for (Node child : root.getChildren()) {
            Node largestChild = protectedFind(child, visited, xorCache);
            int childXOR = memoizedXOR(largestChild, xorCache);
            if (childXOR > max) {
                max = childXOR;
                largest = child;
            }
        }

        return largest;
    }

    /**
     * Memoized version of an XOR accumulator for a given node.
     * 
     * <p>
     * In a deep enough tree, the recursion of finding the XOR value for each
     * child node would quickly get extremely repetitive. By memoizing, we
     * calculate the XOR value for each branch of the tree up front and then
     * return the cached version when asked later. We do, unfortunately, incur
     * autoboxing penalties.
     * 
     * <p>
     * We also have to protect against circular references here, so we inject a
     * 0 value (the identity XOR) before entering recursion.
     * 
     * <p>
     * Note: method is package private for testing purposes.
     * 
     * @param node
     *            Node to calculate an XOR value for.
     * @param cache
     *            Cache of XOR values for each {@code Node} in a given tree.
     * 
     * @return The XOR value of node.
     */
    static int memoizedXOR(Node node, Map<Node, Integer> cache) {
        if (null == node)
            return 0;
        
        if (cache.containsKey(node))
            return cache.get(node);

        if (hasChildren(node)) {
            // To protect against circular references.
            cache.put(node, 0);
            int value = node.getValue();
            for (Node child : node.getChildren())
                value ^= memoizedXOR(child, cache);
            cache.put(node, value);
            return value;
        }
        return node.getValue();
    }

    /**
     * NOTE: This does not guarantee that the children are not null.
     */
    private static boolean hasChildren(Node node) {
        return null != node.getChildren() && node.getChildren().length > 0;
    }
}
