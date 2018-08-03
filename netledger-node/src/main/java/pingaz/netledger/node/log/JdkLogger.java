package pingaz.netledger.node.log;

import java.util.logging.Level;

/**
 * @author ping
 */
public class JdkLogger implements Logger{

    private final java.util.logging.Logger log;

    public JdkLogger(String name){
        this.log = java.util.logging.Logger.getLogger(name);
    }

    @Override
    public void debug(String msg){
        log.fine(msg);
    }

    @Override
    public void debug(String msg, Object... params) {
        log.fine(String.format(msg, params));
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void info(String msg, Object... params) {
        System.out.println(log.getName());
        log.info(String.format(msg, params));
    }

    @Override
    public boolean isDebug() {
        return log.isLoggable(Level.FINE);
    }

    @Override
    public void error(String msg, Throwable throwable) {
        log.log(Level.WARNING, msg, throwable);
    }
}
