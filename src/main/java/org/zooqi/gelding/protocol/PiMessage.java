package org.zooqi.gelding.protocol;

import io.vertx.core.buffer.Buffer;
import org.apache.commons.codec.binary.Hex;
import org.zooqi.gelding.util.ByteUtils;

import java.util.Objects;

/**
 * Pi message definition
 *
 * @author Judge
 */
public class PiMessage {

    /**
     * The Raspberry Pi's ID to this IOT server
     */
    private String id;

    /**
     * Prefix to indicate the protocol type
     */
    private byte prefix;

    /**
     * Version to indicate the protocol version
     */
    private byte version;

    /**
     * The message packet count
     */
    private long serialNumber;

    /**
     * The message payload
     */
    private byte[] payload;

    public static final String SEPARATOR = "\n";

    /**
     * The data packet's default prefix
     */
    public static final byte DEFAULT_PREFIX = 0x6E;

    /**
     * The data packet's default version
     */
    public static final byte DEFAULT_VERSION = 0x01;

    /**
     * The data packet's default ID
     */
    public static final String DEFAULT_ID = "00000000";

    private static final int LENGTH_ID = 4;

    private static final int LENGTH_PREFIX = 1;

    private static final int LENGTH_VERSION = 1;

    private static final int LENGTH_SERIAL_NUMBER = 4;

    private static final int LENGTH_PAYLOAD_LENGTH = 2;

    private static final int LENGTH_NON_PAYLOAD = LENGTH_PREFIX + LENGTH_VERSION +
            LENGTH_ID + LENGTH_SERIAL_NUMBER + LENGTH_PAYLOAD_LENGTH;

    private PiMessage(String id, byte prefix, byte version, long serialNumber, byte[] payload) {
        this.id = id;
        this.prefix = prefix;
        this.version = version;
        this.serialNumber = serialNumber;
        this.payload = payload;
    }

    public static class Builder {

        private String id;

        private byte prefix;

        private byte version;

        private long serialNumber;

        private byte[] payload;

        public Builder(String id, long serialNumber) {
            this(id, DEFAULT_PREFIX, DEFAULT_VERSION, serialNumber);
        }

        public Builder(String id, byte prefix, byte version, long serialNumber) {
            id(id);
            this.prefix = prefix;
            this.version = version;
            serialNumber(serialNumber);

            this.payload = new byte[0];
        }

        public Builder id(String id) {
            Objects.requireNonNull(id);
            if (id.length() != 8) {
                throw new IllegalArgumentException("ID field length must be 8");
            }
            this.id = id;
            return this;
        }

        public Builder prefix(byte prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder version(byte version) {
            this.version = version;
            return this;
        }

        public Builder serialNumber(long serialNumber) {
            if (serialNumber > 0xFFFFFFFFL || serialNumber < 0) {
                throw new IllegalArgumentException("Illegal serialNumber size");
            }

            this.serialNumber = serialNumber;
            return this;
        }

        public Builder payload(byte[] payload) {
            Objects.requireNonNull(payload);
            if (payload.length > 0xFFFF) {
                throw new IllegalArgumentException("The payload size was too large");
            }

            this.payload = payload;
            return this;
        }

        public static Builder fromBuffer(Buffer buffer) {
            return fromBytes(buffer.getBytes());
        }

        public static Builder fromBytes(byte[] bytes) {
            // TODO
            return new Builder("", 1);
        }

        public PiMessage build() {
            return new PiMessage(id, prefix, version, serialNumber, payload);
        }
    }

    public byte prefix() {
        return prefix;
    }

    public byte version() {
        return version;
    }

    public long serialNumber() {
        return serialNumber;
    }

    public byte[] payload() {
        return payload;
    }

    public int length() {
        return LENGTH_NON_PAYLOAD + payloadLength();
    }

    public int payloadLength() {
        return payload.length;
    }

    public Buffer toBuffer() throws Exception {
        return Buffer.buffer(toByteArray());
    }

    public byte[] toByteArray() throws Exception {
        byte[] bytes = new byte[LENGTH_NON_PAYLOAD + payload.length];
        bytes[0] = prefix;
        bytes[1] = version;

        byte[] idBytes = Hex.decodeHex(id.toCharArray());
        System.arraycopy(idBytes, 0, bytes, 2, LENGTH_ID);

        byte[] serialNumberBytes = ByteUtils.toByteArray((int) serialNumber);
        System.arraycopy(serialNumberBytes, 0, bytes, 6, LENGTH_SERIAL_NUMBER);

        byte[] payloadLengthBytes = ByteUtils.toByteArray((short) payload.length);
        System.arraycopy(payloadLengthBytes, 0, bytes, 10, LENGTH_PAYLOAD_LENGTH);

        System.arraycopy(payload, 0, bytes, 12, payload.length);

        return bytes;
    }
}
