package rtb;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public final class DeepCopy {

    /**
     * ByteArrayInputStream implementation that does not synchronize methods.
     */
    private static class FastByteArrayInputStream extends InputStream {

        /** Byte buffer */
        private byte[] buf = null;

        /** Number of bytes that we can read from the buffer */
        private int count = 0;

        /** Number of bytes that have been read from the buffer */
        private int pos = 0;

        private FastByteArrayInputStream(byte[] buf, int count) {
            this.buf = buf;
            this.count = count;
        }

        @Override
        public final int available() {
            return count - pos;
        }

        @Override
        public final int read() {
            return (pos < count) ? (buf[pos++] & 0xff) : -1;
        }

        @Override
        public final int read(byte[] b, int off, int len) {
            if (pos >= count)
                return -1;

            if ((pos + len) > count)
                len = (count - pos);

            System.arraycopy(buf, pos, b, off, len);
            pos += len;
            return len;
        }

        @Override
        public final long skip(long n) {
            if ((pos + n) > count)
                n = count - pos;
            if (n < 0)
                return 0;
            pos += n;
            return n;
        }

    }

    /**
     * ByteArrayOutputStream implementation that doesn't synchronize methods and
     * doesn't copy the data on toByteArray().
     */
    private static class FastByteArrayOutputStream extends OutputStream {

        /** Buffer and size */
        private byte[] buf = null;
        private int size = 0;

        /** Constructs a stream with buffer capacity size 5K */
        private FastByteArrayOutputStream() {
            this(5 * 1024);
        }

        /** Constructs a stream with the given initial size */
        private FastByteArrayOutputStream(int initSize) {
            this.size = 0;
            this.buf = new byte[initSize];
        }

        /** Ensures that we have a large enough buffer for the given size. */
        private void verifyBufferSize(int sz) {
            if (sz > buf.length) {
                byte[] old = buf;
                buf = new byte[Math.max(sz, 2 * buf.length)];
                System.arraycopy(old, 0, buf, 0, old.length);
                old = null;
            }
        }

        @Override
        public final void write(byte b[]) {
            verifyBufferSize(size + b.length);
            System.arraycopy(b, 0, buf, size, b.length);
            size += b.length;
        }

        @Override
        public final void write(byte b[], int off, int len) {
            verifyBufferSize(size + len);
            System.arraycopy(b, off, buf, size, len);
            size += len;
        }

        @Override
        public final void write(int b) {
            verifyBufferSize(size + 1);
            buf[size++] = (byte) b;
        }

        /** Returns a ByteArrayInputStream for reading back the written data */
        private InputStream getInputStream() {
            return new FastByteArrayInputStream(buf, size);
        }

    }

    /**
     * Returns a copy of the object, or null if the object cannot be serialized.
     */
    public static Object copy(Object orig) {
        // TODO: Should orig be something that implements Serializable?
        Object obj = null;
        try {
            // Write the object out to a byte array
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
            obj = in.readObject();
        } catch (IOException ioe) {
            // Completely internal with no IO. Should be impossible
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            // Converting object passed in. Should be impossible
            cnfe.printStackTrace();
        }
        return obj;
    }

}