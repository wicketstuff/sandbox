package org.wicketstuff.jamon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


/**
 * Repository around the JAMon API. This repositories responsibility is to create serializable
 * monitors, since the JAMon {@link Monitor}s are not {@link Serializable}.
 * 
 * @author lars
 * 
 */
public class JamonRepository {

    /**
     * Returns all the {@link SerializableMonitor}s in this repository.
     * 
     * @return List of all {@link SerializableMonitor}s or an empty list if there aren't any.
     */
    public List<SerializableMonitor> getAll() {
        Monitor[] monitors = MonitorFactory.getRootMonitor().getMonitors();

        if (monitors != null) {
            List<SerializableMonitor> serializableMonitors = new ArrayList<SerializableMonitor>(monitors.length);
            for (Monitor monitor : monitors) {
                serializableMonitors.add(new SerializableMonitor(monitor));
            }
            return serializableMonitors;
        } else {
            return new ArrayList<SerializableMonitor>(0);
        }
    }

    /**
     * Returns the number of {@link SerializableMonitor}s in this repository.
     * @return the number of {@link SerializableMonitor}s.
     */
    public int count() {
        Monitor[] monitors = MonitorFactory.getRootMonitor().getMonitors();
        return monitors == null ? 0 : monitors.length;
    }

    /**
     * Removes all the {@link SerializableMonitor}s in this repository. This also
     * propagates to the {@link MonitorFactory}.
     */
    public void clear() {
        MonitorFactory.getFactory().reset();
    }

}
