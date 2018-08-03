package pingaz.netledger.node.handler;

import pingaz.netledger.node.security.PublicCredentials;

/**
 *
 * 接收节点注册的申请，允许该节点注册到网络中
 *
 * @author ping
 */
public class RegisterHandler implements Handler<Boolean, PublicCredentials>{

    @Override
    public Boolean execute(PublicCredentials value) {
        return Boolean.FALSE;
    }
}
