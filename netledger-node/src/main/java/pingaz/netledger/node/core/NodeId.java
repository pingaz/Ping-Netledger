package pingaz.netledger.node.core;

import pingaz.netledger.node.log.Logger;
import pingaz.netledger.node.log.Loggers;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 *
 * 节点ID对象，总共16个byte，生成方式如下：<br/>
 * 1、00-05 byte是时间戳（毫秒） <br/>
 * 2、06-09 byte是机器ID（机器network散列值）<br/>
 * 3、10-11 byte是进程ID <br/>
 * 4、12-15 byte是连接第一个节点ID的散列值 <br/>
 *
 * @author ping
 */
public final class NodeId extends AbstractIdentifier<NodeId> implements Comparable<NodeId> , java.io.Serializable{

    private static final Logger LOGGER = Loggers.getLogger("NodeId");

    public static final NodeId ATOM_NODE_ID = new NodeId(0,0,(short)0,0);

    /**
     * Gets a new node id.
     *
     * @return the new id
     */
    public static NodeId get() {
        return new NodeId();
    }

    /**
     * Gets a new node id.
     *
     * @return the new id
     */
    public static NodeId get(Date date) {
        return new NodeId(date);
    }

    /**
     * Gets a new node id.
     *
     * @return the new id
     */
    public static NodeId get(NodeId joinNodeId) {
        return new NodeId(joinNodeId);
    }

    public static NodeId parseHexString(final String s) {
        if (!isValid(s)) {
            throw new IllegalArgumentException("invalid hexadecimal representation of a NodeId: [" + s + "]");
        }

        byte[] b = new byte[16];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }
        return new NodeId(ByteBuffer.wrap(b));
    }

    public NodeId() {
        this(ATOM_NODE_ID);
    }

    public NodeId(Date date){
        this(ATOM_NODE_ID, date);
    }

    public NodeId(NodeId joinPoint){
        this(joinPoint, new Date());
    }

    public NodeId(NodeId joinPoint, Date date){
        super(joinPoint, date);
    }

    public NodeId(long timestamp, int machineIdentifier, short processIdentifier, int joinNodeIdentifier) {
        super(timestamp, machineIdentifier, processIdentifier, joinNodeIdentifier);
    }

    public NodeId(ByteBuffer buffer){
        super(buffer);
    }

}
