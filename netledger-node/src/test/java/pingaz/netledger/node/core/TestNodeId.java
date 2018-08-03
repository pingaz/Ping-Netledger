package pingaz.netledger.node.core;

import org.junit.jupiter.api.Test;
import pingaz.netledger.node.assertion.Assertions;

import java.util.Date;
import java.util.HashSet;

/**
 * @author ping
 */
public class TestNodeId {

    private void printNode(NodeId id) {
        printNode("", id);
    }

    private void printNode(String prefix, NodeId id) {
        System.out.println(prefix+"Node id hex:"+id.toHexString());
        System.out.println(prefix+"Node id timestamp:"+id.getTimestamp());
        System.out.println(prefix+"Node id machine:"+id.getMachineIdentifier());
        System.out.println(prefix+"Node id process:"+id.getProcessIdentifier());
        System.out.println(prefix+"Node id join identifier:"+id.getJoinNodeIdentifier());
    }

    @Test
    void simpleNodeId(){
        NodeId id = new NodeId();
        printNode(id);
    }

    @Test
    void sameDateNodeId(){
        Date date = new Date();
        NodeId idOne = NodeId.get(date);
        NodeId idTwo = NodeId.get(date);
        Assertions.isTrue("Node id not equals.", idOne.compareTo(idTwo) == 0);
        printNode(idOne);
    }

    @Test
    void linked10000NodeIds(){
        int count = 10000;
        HashSet<String> idSet = new HashSet<>();
        NodeId last = NodeId.ATOM_NODE_ID;
        for(int i=0;i<count;i++){
            NodeId id = new NodeId(last);
            last = id;
            Assertions.isTrue("Node id duplication.", idSet.add(id.toHexString()));
        }
        printNode(last);
    }

    @Test
    void notLinked100NodeIds() throws InterruptedException {
        int count = 100;
        HashSet<String> idSet = new HashSet<>();
        NodeId id = null;
        for(int i=0;i<count;i++){
            id = new NodeId();
            Assertions.isTrue("Node id duplication.", idSet.add(id.toHexString()));
            Thread.sleep(1);
        }
        printNode(id);
    }

    @Test
    void parseNodeId(){

        System.out.println(Long.toHexString(1l<<40));

        NodeId id = new NodeId();
        NodeId parseId = NodeId.parseHexString(id.toHexString());
        printNode("id - ", id);
        printNode("parse id - ", parseId);
        Assertions.isTrue("Node id not equals.", id.equals(parseId));
    }

}
