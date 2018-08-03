package pingaz.netledger.node.core;

import pingaz.netledger.node.security.PublicCredentials;

/**
 * @author ping
 */
public interface Node {

    NodeId getIdentifier();

    Address getAddress();

    PublicCredentials getPublicCredentials();

    boolean exists();

    boolean createNewNode();

}
