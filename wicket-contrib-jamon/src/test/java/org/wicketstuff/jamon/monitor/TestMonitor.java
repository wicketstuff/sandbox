/**
 * 
 */
package org.wicketstuff.jamon.monitor;

import com.jamonapi.NullMonitor;

public class TestMonitor extends NullMonitor {
    private final String label;
    
    public TestMonitor(String label) {
        this.label = label;
    }
    @Override
    public String getLabel() {
        return this.label;
    }

}