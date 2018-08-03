package pingaz.netledger.node.exception;

/**
 * @author ping
 */
public class NodeException extends RuntimeException {

    private final ErrorCode errorCode;

    public NodeException() {
        this(ErrorCode.UNKNOWN);
    }

    public NodeException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public NodeException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public NodeException(String message) {
        this(ErrorCode.UNKNOWN, message);
    }

    public NodeException(Throwable cause) {
        this(ErrorCode.UNKNOWN, cause);
    }

    public NodeException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public NodeException(String message, Throwable cause) {
        this(ErrorCode.UNKNOWN, message, cause);
    }

    public NodeException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
