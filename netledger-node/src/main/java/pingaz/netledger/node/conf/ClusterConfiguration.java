package pingaz.netledger.node.conf;

import pingaz.netledger.node.core.Cluster;
import pingaz.netledger.node.core.ClusterId;
import pingaz.netledger.node.core.Node;
import pingaz.netledger.node.core.NodeId;

/**
 * @author ping
 */
public class ClusterConfiguration {

    public Cluster build(){
        return new Cluster(new ClusterId());
    }
}
