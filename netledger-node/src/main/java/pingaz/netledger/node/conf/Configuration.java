package pingaz.netledger.node.conf;

/**
 * @author ping
 */
public class Configuration {

    private EventListenerList<Configuration> configEventListenerList = new EventListenerList<>();

    private NodeConfiguration nodeCfg;

    private ClusterConfiguration clusterCfg;

    public Configuration(){
        this.nodeCfg = new NodeConfiguration();
        this.clusterCfg = new ClusterConfiguration();
    }

    public NodeConfiguration getNodeConfiguration(){
        return nodeCfg;
    }

    public void addListener(ConfigEventListener<Configuration> listener){
        configEventListenerList.add(listener);
    }


}
