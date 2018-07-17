package org.zooqi.gelding.handler;

import io.vertx.core.Handler;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zooqi.gelding.protocol.PiMessage;
import org.zooqi.gelding.tcp.PiEndpoint;

/**
 * Handler to handle {@link PiMessage}
 *
 * @author Judge
 */
public class PiMessageHandler implements Handler<PiMessage> {

    private final PiEndpoint endpoint;

    private static final Logger LOGGER = LoggerFactory.getLogger(PiMessageHandler.class);

    public PiMessageHandler(PiEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void handle(PiMessage message) {
        try {
            LOGGER.info("Message from endpoint[{}]: {}", endpoint.endpointId(),
                    Hex.encodeHexString(message.toByteArray()));
        } catch (Exception e) {
            LOGGER.warn("Handle message exception", e);
        }
    }
}
