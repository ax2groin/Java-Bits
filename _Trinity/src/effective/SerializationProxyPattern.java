package effective;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

// Adapted from Effective Java, Item 78, 312
public final class SerializationProxyPattern implements Serializable {

    private static final long serialVersionUID = -986044566893615034L;

    private final Date start;
    private final Date end;

    public SerializationProxyPattern(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    private static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = -6037952243785314475L;

        private final Date start;
        private final Date end;

        SerializationProxy(SerializationProxyPattern period) {
            this.start = period.start;
            this.end = period.end;
        }

        private Object readResolve() {
            return new SerializationProxyPattern(start, end); // Uses public constructor
        }
    }

    // writeReplace method for the serialization proxy pattern
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    // readObject method for the serialization proxy pattern
    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}