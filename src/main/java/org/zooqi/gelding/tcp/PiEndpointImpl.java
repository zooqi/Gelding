package org.zooqi.gelding.tcp;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zooqi.gelding.protocol.PiMessage;

/**
 * Implementation of {@link PiEndpoint}
 */
public class PiEndpointImpl implements PiEndpoint {

    private String endpointId;

    private final NetSocket netSocket;

    private static final Logger LOGGER = LoggerFactory.getLogger(PiEndpointImpl.class);

    public PiEndpointImpl(NetSocket netSocket) {
        this.netSocket = netSocket;
        if (netSocket != null) {
            this.endpointId = netSocket.writeHandlerID();
        }
    }

    @Override
    public String endpointId() {
        return this.endpointId;
    }

    @Override
    public PiEndpoint write(PiMessage message) {
        try {
            this.netSocket.write(Buffer.buffer(message.toByteArray()));
        } catch (Exception e) {
            LOGGER.error("Could't write to the stream", e);
        }
        return this;
    }

    @Override
    public PiEndpoint handler(Handler<PiMessage> handler) {
        RecordParser recordParser = RecordParser.newDelimited(PiMessage.SEPARATOR, buffer -> {
            if (handler != null) {
                handler.handle(PiMessage.Builder.fromBuffer(buffer).build());
            }
        });
        netSocket.handler(recordParser);
        return this;
    }

    @Override
    public PiEndpoint closeHandler(Handler<Void> closeHandler) {
        this.netSocket.closeHandler(closeHandler);
        return this;
    }

    @Override
    public PiEndpoint exceptionHandler(Handler<Throwable> throwableHandler) {
        this.netSocket.exceptionHandler(throwableHandler);
        return this;
    }
}
