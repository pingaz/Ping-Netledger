package pingaz.netledger.node.server;

/**
 * @author ping
 */
public interface HttpResponse {

    void setHeader(String name, String value);

    <T> void end(T object);
}
