package pingaz.netledger.node.log;

/**
 * @author ping
 */
public class Loggers {

    public static Logger getLogger(String name){
        return new Log4jLogger(name);
    }

    public static Logger getLogger(Class clazz){
        return new Log4jLogger((clazz.getName()));
    }
}
