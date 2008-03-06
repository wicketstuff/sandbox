package org.wicketstuff.jamon;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public class SerializableMonitorTest {
    private Monitor monitor;

    @Before
    public void setup() {
        monitor = MonitorFactory.getFactory().start("mon").stop();
    }
    
    @After
    public void clear() {
        MonitorFactory.getFactory().reset();
    }
    
    @Test
    public void shouldCopyAllPropertiesFromMonitor() {
        SerializableMonitor serializableMonitor = new SerializableMonitor(monitor);
        assertEquals(monitor.getLabel(), serializableMonitor.getLabel());
        assertEquals(monitor.getAvg(), serializableMonitor.getAvg(), 0);
    }
}
