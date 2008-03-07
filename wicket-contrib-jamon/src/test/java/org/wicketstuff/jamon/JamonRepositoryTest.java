package org.wicketstuff.jamon;

import static org.wicketstuff.jamon.JamonTestUtil.startThisManyMonitors;

import org.junit.After;
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
    
    @After
    public void clear() {
        MonitorFactory.getFactory().reset();
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
    
    
}
