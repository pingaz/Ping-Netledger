package pingaz.netledger.node.conf;

import pingaz.netledger.node.core.Node;
import pingaz.netledger.node.core.NodeId;
import pingaz.netledger.node.core.impl.FileNode;

/**
 * @author ping
 */
public class NodeConfiguration {

    public Node build(){
        return new FileNode(new NodeId());
    }
}
