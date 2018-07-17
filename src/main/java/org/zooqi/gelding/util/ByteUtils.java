package org.zooqi.gelding.util;

/**
 * Utility methods for packing/unpacking primitive values in/out of byte arrays
 *
 * @author Judge
 */
public final class ByteUtils {

    private ByteUtils() {
    }

    /**
     * Convert int value to byte array(Big-Endian)
     *
     * @param val
     * @return
     */
    public static byte[] toByteArray(int val) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (val);
        bytes[2] = (byte) (val >>> 8);
        bytes[1] = (byte) (val >>> 16);
        bytes[0] = (byte) (val >>> 24);
        return bytes;
    }

    /**
     * Convert short value to byte array(Big-Endian)
     *
     * @param val
     * @return
     */
    public static byte[] toByteArray(short val) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (val);
        bytes[0] = (byte) (val >>> 8);
        return bytes;
    }
}
