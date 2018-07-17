package org.zooqi.gelding.tcp;

import io.vertx.core.Handler;
import org.zooqi.gelding.protocol.PiMessage;

/**
 * Raspberry Pi endpoint definition
 *
 * @author Judge
 */
public interface PiEndpoint {

    /**
     * Get the tcp connection id
     *
     * @return
     */
    String endpointId();

    /**
     * Write message to this stream
     *
     * @param message
     * @return
     */
    PiEndpoint write(PiMessage message);

    /**
     * Set the handler to handle the overcoming bytes
     *
     * @param handler
     * @return
     */
    PiEndpoint handler(Handler<PiMessage> handler);

    /**
     * Set the handler to handle socket close
     *
     * @param closeHandler
     * @return
     */
    PiEndpoint closeHandler(Handler<Void> closeHandler);

    /**
     * Set the handler to handle socket exception
     *
     * @param throwableHandler
     * @return
     */
    PiEndpoint exceptionHandler(Handler<Throwable> throwableHandler);
}
