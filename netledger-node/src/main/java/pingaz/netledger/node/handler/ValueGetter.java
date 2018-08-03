package pingaz.netledger.node.handler;

/**
 * @author ping
 */
public interface ValueGetter<V> {

    V get(Class<V> clazz);
}
