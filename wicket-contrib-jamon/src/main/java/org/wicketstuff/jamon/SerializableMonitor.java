/**
 * 
 */
package org.wicketstuff.jamon;

import java.io.Serializable;
import java.util.Date;

import com.jamonapi.Monitor;
import com.jamonapi.Range;

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
    private double active;
    private double avgActive;
    private double avgGlobalActive;
    private double avgPrimaryActive;
    private Date firstAccess;
    private double hits;
    private Date lastAccess;
    private double lastValue;
    private double max;
    private double maxActive;
    private double min;
    private Range range;
    private double stdDev;
    private double total;
    private String units;

    public SerializableMonitor(Monitor monitor) {
        label = monitor.getLabel();
        avg = monitor.getAvg();
        active = monitor.getActive();
        avgActive = monitor.getAvgActive();
        avgGlobalActive = monitor.getAvgGlobalActive();
        avgPrimaryActive = monitor.getAvgPrimaryActive();
        firstAccess = monitor.getFirstAccess();
        hits = monitor.getHits();
        lastAccess = monitor.getLastAccess();
        lastValue = monitor.getLastValue();
        max = monitor.getMax();
        maxActive = monitor.getMaxActive();
        min = monitor.getMin();
        range = monitor.getRange();
        stdDev = monitor.getStdDev();
        total = monitor.getTotal();
        units = monitor.getUnits();
    }

    String getLabel() {
        return label;
    }

    double getAvg() {
        return avg;
    }

    double getActive() {
        return active;
    }

    double getAvgActive() {
        return avgActive;
    }

    double getAvgGlobalActive() {
        return avgGlobalActive;
    }

    double getAvgPrimaryActive() {
        return avgPrimaryActive;
    }

    Date getFirstAccess() {
        return firstAccess;
    }

    double getHits() {
        return hits;
    }

    Date getLastAccess() {
        return lastAccess;
    }

    double getLastValue() {
        return lastValue;
    }

    double getMax() {
        return max;
    }

    double getMaxActive() {
        return maxActive;
    }

    double getMin() {
        return min;
    }

    Range getRange() {
        return range;
    }

    double getStdDev() {
        return stdDev;
    }

    double getTotal() {
        return total;
    }

    String getUnits() {
        return units;
    }
    
}