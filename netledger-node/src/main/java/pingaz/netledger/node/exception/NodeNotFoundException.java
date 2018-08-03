package pingaz.netledger.node.exception;

/**
 * @author ping
 */
public class NodeNotFoundException extends NodeException {
    public NodeNotFoundException() {
        super();
    }

    public NodeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NodeNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NodeNotFoundException(String message) {
        super(message);
    }

    public NodeNotFoundException(Throwable cause) {
        super(cause);
    }

    public NodeNotFoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public NodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
