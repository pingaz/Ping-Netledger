package pingaz.netledger.node.server;

/**
 * @author ping
 */
public interface HttpService {

    void handle(HttpRequest request, HttpResponse response);
}
