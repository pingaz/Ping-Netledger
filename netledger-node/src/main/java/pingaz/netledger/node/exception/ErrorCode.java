package pingaz.netledger.node.exception;

/**
 * @author ping
 */
public enum ErrorCode {

    UNKNOWN(500);

    private int code;

    ErrorCode(int code){
        this.code = code;
    }
}
