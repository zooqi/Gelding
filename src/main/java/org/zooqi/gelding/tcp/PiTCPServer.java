package org.zooqi.gelding.tcp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Raspberry Pi TCP server
 *
 * @author Judge
 */
public interface PiTCPServer {

    /**
     * Static method to create a {@link PiTCPServer}
     *
     * @param vertx
     * @param config
     * @return
     */
    static PiTCPServer create(Vertx vertx, JsonObject config) {
        return new PiTCPServerImpl(vertx, config);
    }

    /**
     * List at default port and host
     *
     * @param listenHandler Handler which will be called when listen succeed
     */
    PiTCPServer listen(Handler<AsyncResult<PiTCPServer>> listenHandler);

    /**
     * List at specified port and host
     *
     * @param port          The specified port
     * @param host          The specified host
     * @param listenHandler Handler which will be called when listen succeed
     */
    PiTCPServer listen(int port, String host, Handler<AsyncResult<PiTCPServer>> listenHandler);

    /**
     * Close the TCP server
     */
    void close();

    /**
     * Close the TCP server
     *
     * @param closeHandler Handler which will be called when close succeed
     */
    void close(Handler<AsyncResult<Void>> closeHandler);

    /**
     * Set a handler when a new endpoint connected
     *
     * @param connectHandler Handler which will be called when Pi endpoint connected
     * @return
     */
    PiTCPServer connectHandler(Handler<PiEndpoint> connectHandler);

    /**
     * Set a handler when exception occurred
     *
     * @param throwableHandler Handler which will be called when Pi endpoint connection exception
     * @return
     */
    PiTCPServer exceptionHandler(Handler<Throwable> throwableHandler);

    /**
     * Get the server's actual port
     *
     * @return
     */
    int actualPort();
}
