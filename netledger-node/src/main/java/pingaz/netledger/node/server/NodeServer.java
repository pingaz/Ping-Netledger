package pingaz.netledger.node.server;

import io.vertx.core.json.JsonObject;
import pingaz.netledger.node.assertion.Assertions;
import pingaz.netledger.node.core.Node;
import pingaz.netledger.node.handler.Dispatcher;
import pingaz.netledger.node.handler.ValueGetter;
import pingaz.netledger.node.log.Logger;
import pingaz.netledger.node.log.Loggers;

/**
 * @author ping
 */
public class NodeServer {

    private static final Logger LOGGER = Loggers.getLogger(NodeServer.class);

    private Node node;
    private VertxServer vertx;
    private Dispatcher dispatcher;

    public NodeServer(Node node){
        this.node = node;
        this.dispatcher = Dispatcher.getDefault(node);
        this.vertx = new VertxServer(node.getAddress().getPort());
    }

    public void startup() {
        if(!node.exists()){
            node.createNewNode();
        }
        vertx.start(new NodeService());
        LOGGER.info("Node server startup with port: %d.", vertx.actualPort());
    }

    public void shutdown() {
        vertx.close();
    }

    private class NodeService implements HttpService{
        @Override
        public void handle(HttpRequest request, HttpResponse response) {
            JsonObject json = request.getBody();
            if (LOGGER.isDebug()) LOGGER.debug("Get request body was: \n%s", json.encodePrettily());

            String operation = json.getString("operation");
            Assertions.notNullArgument("operation", operation);
            JsonObject data = json.getJsonObject("data");

            Object value = data == null ?
                    dispatcher.dispatch(operation, (Void)null) :
                    dispatcher.dispatch(operation, new DataGetter(data));
            response.end(new ResultJson(0, value));
        }
    }
    private static class DataGetter <V> implements ValueGetter<V>{
        JsonObject data;

        public DataGetter(JsonObject data) {
            this.data = data;
        }

        @Override
        public V get(Class<V> clazz) {
            return data.mapTo(clazz);
        }
    }

    private static class ResultJson{
        private int code;
        private Object data;

        public ResultJson(int code, Object data) {
            this.code = code;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "ResultJson{" +
                    "code='" + code + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
