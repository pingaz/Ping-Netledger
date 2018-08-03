package pingaz.netledger.node.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pingaz.netledger.node.TestLogger;
import pingaz.netledger.node.conf.Configuration;
import pingaz.netledger.node.core.Node;

/**
 * @author ping
 */
public class TestNodeServer implements TestLogger {

    @BeforeAll
    void startup(){
        Configuration cfg = new Configuration();
        new NodeServer(cfg.getNodeConfiguration().build()).startup();
    }

}
