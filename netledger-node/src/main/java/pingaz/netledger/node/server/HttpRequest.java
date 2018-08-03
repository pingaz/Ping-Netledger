package pingaz.netledger.node.server;

import io.vertx.core.json.JsonObject;

/**
 * @author ping
 */
public interface HttpRequest {

    <T> T getBody(Class<T> clazz);

    JsonObject getBody();
}
