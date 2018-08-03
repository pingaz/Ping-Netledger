package pingaz.netledger.node.exception;

/**
 * @author ping
 */
public class JsonParseException extends NodeException {

    public JsonParseException() {
    }

    public JsonParseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JsonParseException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }

    public JsonParseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
