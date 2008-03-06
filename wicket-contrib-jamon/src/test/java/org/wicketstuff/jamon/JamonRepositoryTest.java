package org.wicketstuff.jamon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jamonapi.MonitorFactory;


public class JamonRepositoryTest {
    private JamonRepository jamonRepository;
    
    @Before
    public void setup() {
        jamonRepository = new JamonRepository();
        jamonRepository.clear();
    }
    
    @Test
    public void testThatRepositoryReturnsMonitors_OneMonitor() {
        startThisManyMonitors(1);
        
        Assert.assertEquals(1, jamonRepository.count());
        Assert.assertEquals(1, jamonRepository.getAll().size());
    }
    @Test
    public void testThatRepositoryReturnsMonitors_TenMonitors() {
        startThisManyMonitors(10);
        
        Assert.assertEquals(10, jamonRepository.count());
        Assert.assertEquals(10, jamonRepository.getAll().size());
    }

    @Test
    public void testThatRepositoryReturnsMonitors_NoMonitors() {
        startThisManyMonitors(0);
        
        Assert.assertEquals(0, jamonRepository.count());
        Assert.assertEquals(0, jamonRepository.getAll().size());
    }
    
    private void startThisManyMonitors(int numberOfMonitorsToStart) {
        for (int i = 0; i < numberOfMonitorsToStart; i++) {
            MonitorFactory.getFactory().start("mon"+i).stop();
        }
    }
    
    
}
