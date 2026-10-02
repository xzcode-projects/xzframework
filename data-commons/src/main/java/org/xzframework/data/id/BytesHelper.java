package org.xzframework.data.id;

public final class BytesHelper {

    private BytesHelper() {
    }

    /**
     * Interpret a short as its binary form
     *
     * @param shortValue The short to interpret to binary
     * @return The binary
     */
    public static byte[] fromShort(int shortValue) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (shortValue >> 8);
        bytes[1] = (byte) ((shortValue << 8) >> 8);
        return bytes;
    }

    /**
     * Interpret an int as its binary form
     *
     * @param intValue The int to interpret to binary
     * @return The binary
     */
    public static byte[] fromInt(int intValue) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (intValue >> 24);
        bytes[1] = (byte) ((intValue << 8) >> 24);
        bytes[2] = (byte) ((intValue << 16) >> 24);
        bytes[3] = (byte) ((intValue << 24) >> 24);
        return bytes;
    }

    /**
     * Interpret the binary representation of a long.
     *
     * @param bytes The bytes to interpret.
     * @return The long
     */
    public static long asLong(byte[] bytes) {
        return asLong(bytes, 0);
    }

    /**
     * Interpret the binary representation of a long.
     *
     * @param bytes  The bytes to interpret.
     * @param srcPos starting position in the source array.
     * @return The long
     */
    public static long asLong(byte[] bytes, int srcPos) {
        if (bytes == null) {
            return 0;
        }
        final int size = srcPos + 8;
        if (bytes.length < size) {
            throw new IllegalArgumentException("Expecting 8 byte values to construct a long");
        }
        long value = 0;
        for (int i = srcPos; i < size; i++) {
            value = (value << 8) | (bytes[i] & 0xff);
        }
        return value;
    }

}
