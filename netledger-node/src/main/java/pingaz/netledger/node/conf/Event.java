package pingaz.netledger.node.conf;

/**
 * @author ping
 */
public class Event<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
