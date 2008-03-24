package org.wicketstuff.jamon;

import static org.apache.commons.collections.IteratorUtils.toList;
import static org.junit.Assert.assertEquals;
import static org.wicketstuff.jamon.JamonTestUtil.startThisManyMonitors;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public class JamonProviderTest {
    
    private JamonProvider jamonProvider;
    
    @Before
    public void setup() {
        jamonProvider = new JamonProvider();
    }
    
    @After
    @Before
    public void reset() {
        MonitorFactory.getFactory().reset();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldSupportPaging() {
        startThisManyMonitors(15);
        
        Iterator iterator = jamonProvider.iterator(0, 5);
        assertEquals(5, toList(iterator).size());

        iterator = jamonProvider.iterator(4, 5);
        assertEquals(5, toList(iterator).size());

        iterator = jamonProvider.iterator(10, 5);
        assertEquals(5, toList(iterator).size());
        
        iterator = jamonProvider.iterator(15, 5);
        assertEquals(0, toList(iterator).size());
        
        iterator = jamonProvider.iterator(20, 5);
        assertEquals(0, toList(iterator).size());
        
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldSupportSortingOfProperties() {
        startThisManyMonitors(3);
        jamonProvider.setSort("label", true);
        
        Iterator ascendingIterator = jamonProvider.iterator(0, 3);
        assertEquals("mon0", ((Monitor)ascendingIterator.next()).getLabel());
        assertEquals("mon1", ((Monitor)ascendingIterator.next()).getLabel());
        assertEquals("mon2", ((Monitor)ascendingIterator.next()).getLabel());

        jamonProvider.setSort("label", false);
        
        Iterator descendingIterator = jamonProvider.iterator(0, 3);
        assertEquals("mon2", ((Monitor)descendingIterator.next()).getLabel());
        assertEquals("mon1", ((Monitor)descendingIterator.next()).getLabel());
        assertEquals("mon0", ((Monitor)descendingIterator.next()).getLabel());
    }
}
