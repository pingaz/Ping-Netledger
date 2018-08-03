package pingaz.netledger.node.core.impl;

import pingaz.netledger.node.core.Address;
import pingaz.netledger.node.core.Node;
import pingaz.netledger.node.core.NodeId;
import pingaz.netledger.node.core.NodeNetwork;
import pingaz.netledger.node.exception.NodeException;
import pingaz.netledger.node.exception.NodeNotFoundException;
import pingaz.netledger.node.log.Logger;
import pingaz.netledger.node.log.Loggers;
import pingaz.netledger.node.security.NodeCredentials;
import pingaz.netledger.node.security.PublicCredentials;
import pingaz.netledger.node.util.JsonObject;
import pingaz.netledger.node.util.KeyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;

/**
 * @author ping
 */
public class FileNode implements Node {

    private static final Logger LOGGER = Loggers.getLogger(FileNode.class);

    private static final String PUBLIC_CERT_FILE_NAME = "node_rsa.pub";
    private static final String PRIVATE_CERT_FILE_NAME = "node_rsa";
    private static final String NODE_INFO_FILE_NAME = "node";

    public static FileNode build(String joinHexNodeIdentifier){
        NodeId id = new NodeId(NodeId.parseHexString(joinHexNodeIdentifier));
        return new FileNode(id);
    }

    private File folder;

    private NodeId id;

    private NodeCredentials credentials;

    private NodeNetwork network;

    /**
     * Build a node from files of the folder.
     * @param folder
     */
    public FileNode(File folder){
        this.folder = folder;
    }

    public FileNode(NodeId id) {
        this.id = id;
    }

    public String getHexIdentifier(){
        return id.toHexString();
    }

    @Override
    public NodeId getIdentifier(){
        return id;
    }

    @Override
    public Address getAddress(){
        Address address = new Address();
        address.setHost("127.0.0.1");
        address.setPort(9001);
        return address;
    }

    @Override
    public PublicCredentials getPublicCredentials() throws NodeNotFoundException {
        NodeContent nodeContent = load();
        return nodeContent.getPublicCredentials();
    }

    @Override
    public boolean exists() {
        try{
            load();
            return true;
        } catch (NodeNotFoundException e){
            return false;
        }
    }

    @Override
    public synchronized boolean createNewNode() {
        if(exists()){
            return false;
        }
        KeyPair pair = KeyUtils.generateRSAKeyPair();
        NodeContent content = new NodeContent(new NodeId(), pair.getPublic().getEncoded(), pair.getPrivate().getEncoded());
        save(content);
        return true;
    }

    private void save(NodeContent content){
        File node = new File(folder, NODE_INFO_FILE_NAME);
        File publicCert = new File(folder, PUBLIC_CERT_FILE_NAME);
        File privateCert = new File(folder, PRIVATE_CERT_FILE_NAME);

        try {
            saveFile(node, content.getBaseContent());
            saveFile(publicCert, content.getPublicKey());
            saveFile(privateCert, content.getPrivateKey());
        } catch (IOException e) {
            throw new NodeException(e);
        }
    }

    private NodeContent load(){
        File node = new File(folder, NODE_INFO_FILE_NAME);
        File publicCert = new File(folder, PUBLIC_CERT_FILE_NAME);
        File privateCert = new File(folder, PRIVATE_CERT_FILE_NAME);

        if(node.exists() && publicCert.exists() && privateCert.exists()){
            try {
                byte[] nodeContent = readFile(node);
                byte[] publicContent = readFile(publicCert);
                byte[] privateContent = readFile(privateCert);
                byte[] decodeNodeContent = KeyUtils.decryptRSAECBNoPadding(nodeContent, privateContent);
                return new NodeContent(decodeNodeContent, publicContent, privateContent);
            } catch (IOException e) {
                LOGGER.error("Can't read node file, the system should creating a new node.", e);
            }
        }
        throw new NodeNotFoundException();
    }

    private static class NodeContent{
        static final String IDENTIFY = "identify";
        NodeId identify;
        byte[] publicKey;
        byte[] privateKey;

        public NodeContent(byte[] baseContent, byte[] publicKey, byte[] privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
            JsonObject object = new JsonObject(baseContent);
            identify = new NodeId(object.getByteBuffer(IDENTIFY));
        }

        public NodeContent(NodeId identify, byte[] publicKey, byte[] privateKey) {
            this.identify = identify;
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public PublicCredentials getPublicCredentials(){
            PublicCredentials pc = new PublicCredentials();
            byte[] bytes = this.identify.toByteArray();
            pc.setIdentify(bytes);
            pc.setPublicKey(publicKey);
            byte[] hmacKey = KeyUtils.encryptHMAC(publicKey, privateKey);
            pc.setSignature(KeyUtils.encryptHMAC(bytes, hmacKey));
            return pc;
        }

        public byte[] getBaseContent(){
            JsonObject object = new JsonObject();
            object.setBytes(IDENTIFY, identify.toByteArray());
            byte[] encodeContent = KeyUtils.encryptRSAECBNoPadding(object.toByteArray(), publicKey);
            return encodeContent;
        }

        public NodeId getIdentify() {
            return identify;
        }

        public byte[] getPublicKey() {
            return publicKey;
        }

        public byte[] getPrivateKey() {
            return privateKey;
        }
    }

    private static byte[] readFile(File file) throws IOException {
        return java.nio.file.Files.readAllBytes(Paths.get(file.getPath()));
    }

    private static void saveFile(File file, byte[] bytes) throws IOException {
        Path tempFile = Files.createTempFile(Paths.get(file.getParent()), file.getName(), "tmp");
        Path filePath = Paths.get(file.getPath());
        Path bakPath = Paths.get(file.getPath() + ".bak");

        Files.write(tempFile, bytes);
        Files.deleteIfExists(bakPath);
        Files.move(filePath, bakPath);
        Files.move(tempFile, filePath);
        Files.deleteIfExists(tempFile);
    }
}
