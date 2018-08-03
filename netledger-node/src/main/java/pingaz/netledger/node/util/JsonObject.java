package pingaz.netledger.node.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pingaz.netledger.node.exception.JsonParseException;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author ping
 */
public class JsonObject {

    private ObjectNode object;

    public JsonObject(byte[] json){
        try {
            object = (ObjectNode) new ObjectMapper().readTree(json);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    public JsonObject() {
        object = new ObjectMapper().createObjectNode();
    }

    public String getString(String name){
        return object.get(name).asText();
    }

    public ByteBuffer getByteBuffer(String name){
        try {
            return ByteBuffer.wrap(object.get(name).binaryValue());
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    public void setBytes(String name, byte[] buffer){
        object.put(name, buffer);
    }

    public byte[] toByteArray(){
        try {
            return new ObjectMapper().writeValueAsBytes(object);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

}
