package pingaz.netledger.node.server;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import pingaz.netledger.node.exception.NodeException;

/**
 * @author Pingaz
 */
public class VertxServer {

    private int port;
    private Vertx vertx;
    private HttpServer httpServer;

    public VertxServer(int port){
        this.port = port;
        vertx = Vertx.vertx();
    }

    public void start(HttpService httpService) {
        if(httpServer==null){
            httpServer = vertx.createHttpServer();
            httpServer.requestHandler(req -> {
                final HttpServerResponse httpServerResponse = req.response();
                req.bodyHandler(handler -> {
                    VertxHttpRequest vhr = new VertxHttpRequest(handler);
                    VertxHttpResponse res = new VertxHttpResponse(httpServerResponse);
                    httpServerResponse.putHeader("content-type", "application/json");
                    httpService.handle(vhr, res);
                });
            }).listen(port);
        }else{
            throw new NodeException("Vertx server was started.");
        }
    }

    public void close(){
        vertx.close();
    }

    public int actualPort(){
        if(httpServer==null){
            throw new NodeException("Vertx server has not started.");
        }
        return httpServer.actualPort();
    }

    private static class VertxHttpRequest implements HttpRequest{
        final Buffer buffer;

        public VertxHttpRequest(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public <T> T getBody(Class<T> clazz) {
            return buffer.toJsonObject().mapTo(clazz);
        }

        @Override
        public JsonObject getBody() {
            return buffer.toJsonObject();
        }
    }

    private static class VertxHttpResponse implements HttpResponse{
        final HttpServerResponse response;

        public VertxHttpResponse(HttpServerResponse response) {
            this.response = response;
        }

        @Override
        public void setHeader(String name, String value){
            response.putHeader(name, value);
        }

        @Override
        public <T> void end(T object) {
            response.end(Json.encodeToBuffer(object));
        }
    }
}
