package org.zooqi.gelding;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The program launcher
 *
 * @author Judge
 */
public class Launcher {

    private static final String CONFIG_PATH = "config.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(config(vertx));
        vertx.deployVerticle(MainVerticle.class, options, ar -> {
            if (ar.failed()) {
                LOGGER.warn("Fail to deploy The Pi server", ar.cause());
            } else {
                LOGGER.info("The Pi server has been deployed successfully: {}", ar.result());
            }
        });
    }

    /**
     * Read config file from classpath
     *
     * @param vertx
     * @return
     */
    private static JsonObject config(Vertx vertx) {
        Buffer buffer = vertx.fileSystem().readFileBlocking(CONFIG_PATH);
        return new JsonObject(buffer);
    }
}
