package pingaz.netledger.node.exception;

/**
 * @author ping
 */
public class SecurityException extends NodeException {
    public SecurityException() {
    }

    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SecurityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }

    public SecurityException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
