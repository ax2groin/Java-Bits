package rtb;

import java.lang.reflect.Field;

public class Play {

    /**
     * Simple demonstration of the ability to use reflection to implement a swap
     * method in Java, regardless of fields being private, final, primitive, or
     * whatever.
     * <p>
     * This is just a proof of concept. It assumes that the fields are of the
     * same type and reside in the same object.
     * 
     * @param obj
     *            Object containing two fields to swap. Both fields must be of
     *            the same type.
     * @param left
     *            Name of first field to swap with second field.
     * @param right
     *            Name of second field to swap with first field.
     * 
     * @throws SecurityException
     *             if the field cannot be accessed, as per
     *             {@link Class#getDeclaredField(String)}.
     * @throws NoSuchFieldException
     *             if the fields to be swapped do not exist.
     * @throws IllegalArgumentException
     *             if the specified object is not an instance of the class or
     *             interface declaring the underlying field (or a subclass or
     *             implementor thereof), or if an unwrapping conversion fails.
     * @throws IllegalAccessException
     *             if this Field object is enforcing Java language access
     *             control and the underlying field is either inaccessible or
     *             final.
     */
    public static void swap(Object obj, String left, String right)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        if (obj.getClass().getDeclaredField(left).getType()
                != obj.getClass().getDeclaredField(right).getType())
            throw new IllegalArgumentException(left + " and " + right + " are not the same Type.");

        Field leftField = obj.getClass().getDeclaredField(left);
        Field rightField = obj.getClass().getDeclaredField(right);
        
        // Override final and private
        boolean lMutable = leftField.isAccessible();
        if (!lMutable)
            leftField.setAccessible(true);
        boolean rMutable = rightField.isAccessible();
        if (!rMutable)
            rightField.setAccessible(true);

        // Do the Swap
        Object temp = leftField.get(obj);
        leftField.set(obj, rightField.get(obj));        
        rightField.set(obj, temp);
        
        // Reset mutability flags
        if (!lMutable)
            leftField.setAccessible(false);
        if (!rMutable)
            rightField.setAccessible(false);
    }
}