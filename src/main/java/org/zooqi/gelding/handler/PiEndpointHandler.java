package org.zooqi.gelding.handler;

import io.vertx.core.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zooqi.gelding.tcp.PiEndpoint;

/**
 * Handler to handle Raspberry Pi endpoint
 *
 * @author Judge
 */
public class PiEndpointHandler implements Handler<PiEndpoint> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PiEndpointHandler.class);

    @Override
    public void handle(PiEndpoint endpoint) {
        LOGGER.info("New endpoint connected: {}", endpoint.endpointId());
        endpoint.handler(new PiMessageHandler(endpoint));
        endpoint.closeHandler(v -> handleClose(endpoint));
        endpoint.exceptionHandler(throwable -> handleException(endpoint, throwable));
    }

    /**
     * Handle connection closed
     *
     * @param endpoint
     */
    private void handleClose(PiEndpoint endpoint) {
        LOGGER.warn("Endpoint {} closed", endpoint.endpointId());
    }

    /**
     * Handle connection exception
     *
     * @param endpoint
     * @param throwable
     */
    private void handleException(PiEndpoint endpoint, Throwable throwable) {
        LOGGER.warn("Endpoint {} connection exception", throwable);
    }
}
