package effective;

public final class LazyInitialization {

    // Lazy initialization holder class idiom for static fields
    // Effective Java, 283
    private static class FieldHolder {

        static final FieldType field = computeFieldValue();

        private static FieldType computeFieldValue() {
            // complex calculation here
            return null;
        }
    }

    static FieldType getField() {
        return FieldHolder.field;
    }

    private class FieldType {

    }

    // Double-check idiom for lazy initialization of instance fields
    // Effective java, 283
    private volatile FieldType field;

    FieldType getValue() {
        FieldType result = field;
        if (result == null) { // First check (no locking)
            synchronized (this) {
                result = field;
                if (result == null) // Second check (with locking)
                    field = result = computeFieldValue();
            }
        }
        return result;
    }

    private FieldType computeFieldValue() {
        // complex calculation here
        return null;
    }
}