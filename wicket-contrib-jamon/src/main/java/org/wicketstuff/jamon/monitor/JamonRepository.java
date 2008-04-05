package org.wicketstuff.jamon.monitor;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


/**
 * <p>
 * Repository around the Jamon API. This repository is a small wrapper around the
 * {@link MonitorFactory}.
 * </p>
 * <p>
 * Since wicket is an unmanged framework this repository is implemented as a
 * singleton. All monitors are kept in memory anyway so that shouldn't pose
 * a problem. One can use the {@link #clear()} method to remove all
 * {@link Monitor}s from this repository.
 * </p>
 * @author lars
 * 
 */
@SuppressWarnings("serial")
public class JamonRepository implements Serializable {
    private static final JamonRepository jamonRepository = new JamonRepository();
    
    private JamonRepository() {
    }
    
    public static JamonRepository getJamonRepository() {
        return jamonRepository;
    }
    /**
     * Returns all the {@link Monitor}s in this repository.
     * 
     * @return List of all {@link Monitor}s or an empty list if there aren't any.
     */
    List<Monitor> getAll() {
        Monitor[] monitors = MonitorFactory.getRootMonitor().getMonitors();

        if (monitors != null) {
            return Arrays.asList(monitors);
        } else {
            return new ArrayList<Monitor>(0);
        }
    }

    /**
     * Returns the number of {@link Monitor}s in this repository.
     * 
     * @return the number of {@link Monitor}s.
     */
    public int count() {
        return getAll().size();
    }

    /**
     * Removes all the {@link SerializableMonitor}s in this repository. This also propagates to the
     * {@link MonitorFactory}.
     */
    public static void clear() {
        MonitorFactory.getFactory().reset();
    }

    /**
     * Returns {@link Monitor} that registered under the given <code>monitorLabel</code>
     * 
     * @param monitorLabel The label of the monitor to be returned
     * @return The found monitor or <code>null</code>.
     */
    public Monitor findMonitorByLabel(String monitorLabel) {
        if (monitorLabel == null) {
            throw new IllegalArgumentException("monitorLabel is null");
        }
        List<Monitor> monitors = find(new MonitorLabelSpecification(monitorLabel));
        
        if(monitors.size() > 1) {
            throw new IllegalStateException(format("More than one monitor with label '%s' found", monitorLabel));
        }
        return monitors.isEmpty() ? null : monitors.get(0);
    }

    /**
     * Returns all {@link Monitor} that satisfy the given {@link MonitorSpecification}.
     * @param specification The {@link MonitorSpecification} to satisfy
     * @return All monitors that satisfy the given {@link MonitorSpecification}.
     */
    public List<Monitor> find(MonitorSpecification specification) {
        List<Monitor> result = new ArrayList<Monitor>();
        for (Monitor monitor : getAll()) {
            if (specification.isSatisfiedBy(monitor)) {
                result.add(monitor);
            }
        }
        return result;
    }

}
