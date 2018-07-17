package org.zooqi.gelding.tcp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * Implementation of {@link PiTCPServer}
 *
 * @author Judge
 */
public class PiTCPServerImpl implements PiTCPServer {

    private final NetServer netServer;

    private final NetServerOptions options;

    private static final int DEFAULT_PORT = 8200;

    private static final String DEFAULT_HOST = "0.0.0.0";

    public PiTCPServerImpl(Vertx vertx, JsonObject config) {
        this.options = new NetServerOptions(config);
        this.netServer = vertx.createNetServer(options);
    }

    @Override
    public PiTCPServer listen(Handler<AsyncResult<PiTCPServer>> listenHandler) {
        int port = options.getPort() == 0 ? DEFAULT_PORT : options.getPort();
        String host = options.getHost() == null ? DEFAULT_HOST : options.getHost();
        return listen(port, host, listenHandler);
    }

    @Override
    public PiTCPServer listen(int port, String host, Handler<AsyncResult<PiTCPServer>> listenHandler) {
        this.netServer.listen(port, host, ar -> {
            if (listenHandler == null) {
                return;
            }

            if (ar.failed()) {
                listenHandler.handle(Future.failedFuture(ar.cause()));
            } else {
                listenHandler.handle(Future.succeededFuture(this));
            }
        });
        return this;
    }

    @Override
    public void close() {
        this.netServer.close();
    }

    @Override
    public void close(Handler<AsyncResult<Void>> closeHandler) {
        this.netServer.close(closeHandler);
    }

    @Override
    public PiTCPServer connectHandler(Handler<PiEndpoint> connectHandler) {
        if (connectHandler != null) {
            this.netServer.connectHandler(netSocket -> connectHandler.handle(new PiEndpointImpl(netSocket)));
        }
        return this;
    }

    @Override
    public PiTCPServer exceptionHandler(Handler<Throwable> throwableHandler) {
        this.netServer.exceptionHandler(throwableHandler);
        return this;
    }

    @Override
    public int actualPort() {
        return this.netServer.actualPort();
    }
}
