package pingaz.netledger.node.handler;

import pingaz.netledger.node.core.Node;
import pingaz.netledger.node.security.PublicCredentials;

/**
 *
 * 获取节点公开凭证的操作类，返回本节点公开凭证。
 *
 * @author ping
 */
public class NodePublicCredentialsHandler implements Handler<PublicCredentials, Void>{

    private final Node node;

    public NodePublicCredentialsHandler(Node node) {
        this.node = node;
    }

    @Override
    public PublicCredentials execute(Void empty) {
        return node.getPublicCredentials();
    }
}
