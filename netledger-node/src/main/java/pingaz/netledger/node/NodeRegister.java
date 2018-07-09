package pingaz.netledger.node;

/**
 * @author Pingaz
 */
public interface NodeRegister {

    void register(NodeConnection parentNode);

    void register(ClusterConnection cluster);

}
