/**
 * 
 */
package org.wicketstuff.jamon;

import java.io.Serializable;

import com.jamonapi.Monitor;

/**
 * Class that is the {@link Serializable} counterpart of the {@link Monitor}.
 * It is basically just a copy that implements the {@link Serializable} interface.
 * 
 * @author lars
 *
 */
@SuppressWarnings({"serial", "unused"})
class SerializableMonitor implements Serializable {

    private String label;
    private double avg;

    public SerializableMonitor(Monitor monitor) {
        label = monitor.getLabel();
        avg = monitor.getAvg();
    }

    String getLabel() {
        return label;
    }

    double getAvg() {
        return avg;
    }
    
}