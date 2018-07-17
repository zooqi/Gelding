package org.zooqi.gelding.protocol;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PiMessageTest {

    private PiMessage message;

    @Before
    public void setUp() throws Exception {
        String id = "FEF0F0F0";
        long serialNumber = 0xFFFFFFFFL - 1;

        byte[] payload = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        this.message = new PiMessage.Builder(id, serialNumber)
                .payload(payload)
                .build();
    }

    @Test
    public void prefix() {
        Assert.assertEquals(PiMessage.DEFAULT_PREFIX, message.prefix());
    }

    @Test
    public void version() {
        Assert.assertEquals(PiMessage.DEFAULT_VERSION, message.version());
    }

    @Test
    public void serialNumber() {
        Assert.assertEquals(0xFFFFFFFFL - 1, message.serialNumber());
    }

    @Test
    public void payload() {
        Assert.assertArrayEquals(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05}, message.payload());
    }

    @Test
    public void length() {
        Assert.assertEquals(17, message.length());
    }

    @Test
    public void payloadLength() {
        Assert.assertEquals(5, message.payloadLength());
    }

    @Test
    public void toByteArray() {
        try {
            String hex = Hex.encodeHexString(message.toByteArray()).toUpperCase();

            StringBuilder sb = new StringBuilder(hex);
            sb.insert(2, "|");
            sb.insert(5, "|");
            sb.insert(14, "|");
            sb.insert(23, "|");
            sb.insert(28, "|");

            System.out.println(sb.toString());
            System.out.println("Message length: " + message.length() + ", Payload length: " + message.payloadLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}