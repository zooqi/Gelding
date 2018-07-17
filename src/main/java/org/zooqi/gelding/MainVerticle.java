package org.zooqi.gelding;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zooqi.gelding.handler.PiEndpointHandler;
import org.zooqi.gelding.tcp.PiTCPServer;

/**
 * The Main Verticle
 *
 * @author Judge
 */
public class MainVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        try {
            PiTCPServer tcpServer = PiTCPServer.create(vertx, config());

            tcpServer.exceptionHandler(throwable -> handleException(throwable, tcpServer))
                    .connectHandler(new PiEndpointHandler())
                    .listen(ar -> {
                        if (ar.failed()) {
                            LOGGER.warn("Fail to start up The Pi server", ar.cause());
                            startFuture.fail(ar.cause());
                        } else {
                            LOGGER.info("The Pi server listen at port: {}", ar.result().actualPort());
                            startFuture.complete();
                        }
                    });
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void handleException(Throwable throwable, PiTCPServer tcpServer) {
        LOGGER.warn("The Pi server exception", throwable);
        tcpServer.close();
    }
}
