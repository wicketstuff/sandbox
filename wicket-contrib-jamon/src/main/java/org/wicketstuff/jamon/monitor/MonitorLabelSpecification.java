package org.wicketstuff.jamon.monitor;

import com.jamonapi.Monitor;

/**
 * Specification that is satisfied whenever the label of the {@link Monitor}
 * is the same as the {@link #label} of this specification. Case is not ignored.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class MonitorLabelSpecification implements MonitorSpecification {
    
    private final String label;

    /**
     * Construct.
     * @param label The label for this specification.
     */
    public MonitorLabelSpecification(String label) {
        this.label = label;
    }
    
    /**
     * @return <code>true</code> is the label of the given  {@link Monitor} is exactly
     * the same as this {@link #label}, <code>false</code> if otherwise.
     */
    public boolean isSatisfiedBy(Monitor monitor) {
        return label.equals(monitor.getLabel());
    }

}
