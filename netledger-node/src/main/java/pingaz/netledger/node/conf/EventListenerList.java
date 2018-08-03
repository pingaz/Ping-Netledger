package pingaz.netledger.node.conf;

import java.util.ArrayList;

/**
 * @author ping
 */
public class EventListenerList <T>{

    ArrayList<ConfigEventListener<T>> listeners = new ArrayList<>(1);

    public void add(ConfigEventListener<T> listener){
        listeners.add(listener);
    }

    public void update(Event<T> event){
        for(ConfigEventListener<T> l : listeners){
            l.update(event);
        }
    }
}
