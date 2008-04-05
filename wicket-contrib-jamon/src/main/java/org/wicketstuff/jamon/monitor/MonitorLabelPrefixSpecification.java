package org.wicketstuff.jamon.monitor;

import com.jamonapi.Monitor;

/**
 * Class that checks if a {@link Monitor}s label starts with the {@link #firstPartOfLabel}.
 * Case is ignored in this check.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class MonitorLabelPrefixSpecification implements MonitorSpecification {
    private final String firstPartOfLabel;
    /**
     * Construct.
     * @param firstPartOfLabel the first part of the label.
     */
    public MonitorLabelPrefixSpecification(String firstPartOfLabel) {
        this.firstPartOfLabel = firstPartOfLabel.toLowerCase();
    }
    /**
     * Does the given {@link Monitor}s label starts with the {@link #firstPartOfLabel}.
     */
    public boolean isSatisfiedBy(Monitor monitor) {
        return monitor.getLabel().toLowerCase().startsWith(firstPartOfLabel);
    }

}
