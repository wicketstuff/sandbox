package org.wicketstuff.jamon.monitor;


import com.jamonapi.Monitor;

/**
 * Specification that always returns true.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class AlwaysSatisfiedMonitorSpecification implements MonitorSpecification {

    public boolean isSatisfiedBy(Monitor monitor) {
        return true;
    }

}
