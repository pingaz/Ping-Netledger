package pingaz.netledger.node.handler;

import pingaz.netledger.node.assertion.Assertions;
import pingaz.netledger.node.core.Node;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ping
 */
public class Dispatcher {

    public static Dispatcher getDefault(Node node){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.putHandler("register-node", new RegisterHandler());
        dispatcher.putHandler("node-public", new NodePublicCredentialsHandler(node));
        return dispatcher;
    }

    private ConcurrentHashMap<String, HandlerInstance> handlers = new ConcurrentHashMap<>();

    public <R, V> R dispatch(String name, V value){
        HandlerInstance handler = getHandlerInstance(name);
        return (R) handler.getHandler().execute(value);
    }

    public <R, V> R dispatch(String name, ValueGetter<V> getter){
        HandlerInstance handler = getHandlerInstance(name);
        return (R) handler.getHandler().execute(getter.get((Class<V>) handler.getValueClass()));
    }

    private HandlerInstance getHandlerInstance(String name) {
        HandlerInstance handler = handlers.get(name);
        Assertions.notNull(handler, "Not found a handler by name - %s.", name);
        return handler;
    }

    public void putHandler(String name, Handler handler){
        handlers.put(name, new HandlerInstance(handler));
    }

    static class HandlerInstance{
        Handler handler;
        Class<?> valueClass;
        Class<?> returnClass;

        public HandlerInstance(Handler handler){
            this.handler = handler;
            Type[] types = handler.getClass().getGenericInterfaces();
            for(Type t : types){
                if(t instanceof  ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) t;
                    if (parameterizedType.getRawType().equals(Handler.class)){
                        Type[] classes = parameterizedType.getActualTypeArguments();
                        returnClass = (Class)classes[0];
                        valueClass = (Class)classes[1];
                        return;
                    }
                }
            }

        }

        public Handler getHandler() {
            return handler;
        }

        public Class<?> getValueClass() {
            return valueClass;
        }

        public Class<?> getReturnClass() {
            return returnClass;
        }
    }
}
