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
public final class ClusterId extends AbstractIdentifier<ClusterId> implements Comparable<ClusterId> , java.io.Serializable{

    private static final Logger LOGGER = Loggers.getLogger("NodeId");

    public static final ClusterId ATOM_NODE_ID = new ClusterId(0,0,(short)0,0);

    /**
     * Gets a new node id.
     *
     * @return the new id
     */
    public static ClusterId get() {
        return new ClusterId();
    }

    /**
     * Gets a new node id.
     *
     * @return the new id
     */
    public static ClusterId get(Date date) {
        return new ClusterId(date);
    }

    /**
     * Gets a new node id.
     *
     * @return the new id
     */
    public static ClusterId get(ClusterId joinNodeId) {
        return new ClusterId(joinNodeId);
    }

    public static ClusterId parseHexString(final String s) {
        if (!isValid(s)) {
            throw new IllegalArgumentException("invalid hexadecimal representation of a NodeId: [" + s + "]");
        }

        byte[] b = new byte[16];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }
        return new ClusterId(ByteBuffer.wrap(b));
    }

    public ClusterId() {
        this(ATOM_NODE_ID);
    }

    public ClusterId(Date date){
        this(ATOM_NODE_ID, date);
    }

    public ClusterId(ClusterId joinPoint){
        this(joinPoint, new Date());
    }

    public ClusterId(ClusterId joinPoint, Date date){
        super(joinPoint, date);
    }

    public ClusterId(long timestamp, int machineIdentifier, short processIdentifier, int joinNodeIdentifier) {
        super(timestamp, machineIdentifier, processIdentifier, joinNodeIdentifier);
    }

    public ClusterId(ByteBuffer buffer){
        super(buffer);
    }

}
