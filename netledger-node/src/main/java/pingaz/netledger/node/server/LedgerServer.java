package pingaz.netledger.node.server;

import pingaz.netledger.node.conf.*;

import java.util.EventListener;

/**
 * @author ping
 */
public class LedgerServer {

    public static void main(String[] args){
        new LedgerServer().startup();
    }

    public LedgerServer(Configuration config){
        this.load(config);
    }

    public LedgerServer(){
        this(new Configuration());
    }

    private NodeServer nodeServer;

    private void load(Configuration config){
        if(nodeServer!=null){
            nodeServer.shutdown();
        }
        nodeServer = new NodeServer(config.getNodeConfiguration().build());
    }

    public void startup(){
        nodeServer.startup();
    }

    public void shutdown(){

    }

    private class ConfigurationWatcher implements ConfigEventListener<Configuration>{

        @Override
        public void update(Event<Configuration> event) {
            load(event.getValue());
        }
    }
}
