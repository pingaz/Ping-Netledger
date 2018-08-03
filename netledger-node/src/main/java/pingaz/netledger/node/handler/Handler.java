package pingaz.netledger.node.handler;

/**
 * @author ping
 */
public interface Handler <R, V>{

    R execute(V value);
}
