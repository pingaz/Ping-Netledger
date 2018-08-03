package pingaz.netledger.node.conf;

/**
 * @author ping
 */
public interface ConfigEventListener <T>{

    void update(Event<T> event);
}
