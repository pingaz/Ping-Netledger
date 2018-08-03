package pingaz.netledger.node.security;

/**
 * @author ping
 */
public class PublicCredentials {
    private byte[] identify;
    private byte[] publicKey;
    private byte[] signature;

    public byte[] getIdentify() {
        return identify;
    }

    public void setIdentify(byte[] identify) {
        this.identify = identify;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
