package job.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 8.3 Write a method that returns all subsets of a set.
 */
public final class Combinatorics {

    /**
     * Return all n-sized combinations of a set.
     * 
     * NOTE: Implicit assumption that coll does not contain duplicates.
     *       Using a List because Java's Set does not allow us to grab the first item.
     * 
     * @param coll
     *            Collection from which to draw combinations.
     * @param n
     *            Number of elements in each combination.
     * 
     * @return A Set of Sets containing each combination.
     */
    public static <T> Set<List<T>> combinations(List<T> coll, int n) {
        Set<List<T>> combinations = new HashSet<List<T>>();

        if (coll.isEmpty() || n == 0) {
            combinations.add(new ArrayList<T>());
        } else if (n == coll.size()) {
            combinations.add(coll);
        } else {
            // Need to copy it, otherwise it is destructive
            List<T> copy = new ArrayList<T>(coll);
            T first = copy.remove(0);
            
            for (List<T> list : combinations(copy, n - 1)) {
                list.add(first);
                combinations.add(list);
            }

            for (List<T> list : combinations(copy, n))
                combinations.add(list);
        }
        return combinations;
    }

    /**
     * Get all subsets from a given set.
     * 
     * @param base
     *            Set from which to draw all subsets.
     * 
     * @return A Set of Sets, containing all the subsets of the base.
     */
    public static <T> Set<List<T>> subsetsOf(List<T> coll) {
        Set<List<T>> subsets = new HashSet<List<T>>();
        for (int i = 0; i <= coll.size(); i++)
            subsets.addAll(combinations(coll, i));
        return subsets;
    }

}
