package pingaz.netledger.node.log;

/**
 * @author ping
 */
public interface Logger {
    void debug(String msg);

    void debug(String msg, Object... params);

    void info(String msg);

    void info(String msg, Object... params);

    boolean isDebug();

    void error(String msg, Throwable throwable);
}
