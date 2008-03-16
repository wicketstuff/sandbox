package org.wicketstuff.jamon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


/**
 * Repository around the Jamon API. This repository is a small wrapper around
 * the {@link MonitorFactory}.
 * 
 * @author lars
 * 
 */
@SuppressWarnings("serial")
public class JamonRepository implements Serializable {

    /**
     * Returns all the {@link Monitor}s in this repository.
     * 
     * @return List of all {@link Monitor}s or an empty list if there aren't any.
     */
    public List<Monitor> getAll() {
        Monitor[] monitors = MonitorFactory.getRootMonitor().getMonitors();

        if (monitors != null) {
            return Arrays.asList(monitors);
        } else {
            return new ArrayList<Monitor>(0);
        }
    }

    /**
     * Returns the number of {@link Monitor}s in this repository.
     * @return the number of {@link Monitor}s.
     */
    public int count() {
        return getAll().size();
    }

    /**
     * Removes all the {@link SerializableMonitor}s in this repository. This also
     * propagates to the {@link MonitorFactory}.
     */
    public void clear() {
        MonitorFactory.getFactory().reset();
    }

}
