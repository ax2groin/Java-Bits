package effective;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * <p>
 * This class is ( immutable | unconditionally thread-safe | conditionally
 * thread-safe | not thread-safe | thread-hostile ).
 * 
 * @author daines
 */
// Document thread safety, Effective Java, Item 70, 278
public class JavaDocExamples implements Serializable {

    private static final long serialVersionUID = -7331872052151641364L;

    /**
     * Last name. Must be non-null.
     * 
     * @serial
     */
    // Document serializable fields
    // Effective Java, 295
    private final String lastName;

    /**
     * The triangle inequality is {@literal |x + y| < |x| + |y|}.
     */
    // Use literal instead of <pre> tags
    private String literal;

    /**
     * A college degree, such as B.S., {@literal M.S.} or Ph.D.
     * 
     * <p>
     * If you didn't use the literal above (or a comma), the first line that
     * becomes the "summary description" wouldn't include everything intended,
     * as it is delimited by a period followed by a whitespace.
     * 
     * @param lastName
     *            Family name to be assigned to this object.
     */
    public JavaDocExamples(String lastName) {
        if (lastName == null)
            throw new NullPointerException("Last name must be non-null.");
        this.lastName = lastName;
    }

    /**
     * Serialize this {@code JavaDocExamples} instance.
     * 
     * @serialData The size of the list (the number of string it contains) is
     *             emitted ({@code int}), followed by all of its elements (each
     *             a {@code String}), in the proper sequence.
     */
    // Even private serialization methods should be documented for classes which
    // can be extended.
    // Effective Java, 298
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * An object that maps keys to values. A map cannot contain duplicate keys:
     * each key can map to at most one value.
     * 
     * @param <K>
     *            the type of keys maintained in this map
     * @param <V>
     *            the type of mapped values
     */
    // Document generic type arguments
    // Effective Java, 206
    public interface Map<K, V> {

    }

    // Document each constant in an enum
    // Effective Java, 207
    public enum OrchestraSection {
        /** Woodwinds, such as flute, clarinet, and oboe. */
        WOODWIND,

        /** Brass instruments, such as French horn and trumped. */
        BRASS,

        /** Percussion instruments, such as timpani and cymbals. */
        PERCUSSION,

        /** Stringed instruments, such as violin and cello. */
        STRING,
    }
}