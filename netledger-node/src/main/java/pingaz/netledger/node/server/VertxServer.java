package pingaz.netledger.node.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * @author Pingaz
 */
public class VertxServer extends AbstractVerticle {

    static int count = 8080;

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(VertxServer.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World!");
        }).listen(count++);
    }
}
