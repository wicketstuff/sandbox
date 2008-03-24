package org.wicketstuff.jamon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.wicketstuff.jamon.JamonTestUtil.MONITOR_PREFIX;
import static org.wicketstuff.jamon.JamonTestUtil.startThisManyMonitors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public class JamonRepositoryTest {
    private JamonRepository jamonRepository;
    
    @Before
    public void setup() {
        jamonRepository = new JamonRepository();
        jamonRepository.clear();
    }
    
    @After
    public void clear() {
        MonitorFactory.getFactory().reset();
    }
    @Test
    public void testThatRepositoryReturnsMonitors_OneMonitor() {
        startThisManyMonitors(1);
        
        assertEquals(1, jamonRepository.count());
        assertEquals(1, jamonRepository.getAll().size());
    }
    @Test
    public void testThatRepositoryReturnsMonitors_TenMonitors() {
        startThisManyMonitors(10);
        
        assertEquals(10, jamonRepository.count());
        assertEquals(10, jamonRepository.getAll().size());
    }

    @Test
    public void testThatRepositoryReturnsMonitors_NoMonitors() {
        startThisManyMonitors(0);
        
        assertEquals(0, jamonRepository.count());
        assertEquals(0, jamonRepository.getAll().size());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void shouldFailIfTryingToFindMonitorWithNullLabel() {
        jamonRepository.findMonitorByLabel(null);
    }
    
    @Test
    public void shouldReturnNullIfNoMonitorIsFoundForACertainLabel() {
        startThisManyMonitors(1);
        assertNull(jamonRepository.findMonitorByLabel("non existing label"));
    }
    
    @Test
    public void shouldReturnMonitorWithGivenLabel() {
        startThisManyMonitors(2);
        Monitor actual = jamonRepository.findMonitorByLabel(MONITOR_PREFIX+"1");
        
        assertNotNull(actual);
        assertEquals(MONITOR_PREFIX+"1", actual.getLabel());
    }
    
    
}
