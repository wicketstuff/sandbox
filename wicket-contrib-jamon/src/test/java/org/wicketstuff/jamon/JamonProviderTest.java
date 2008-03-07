package org.wicketstuff.jamon;

import static org.apache.commons.collections.IteratorUtils.toList;
import static org.junit.Assert.assertEquals;
import static org.wicketstuff.jamon.JamonTestUtil.startThisManyMonitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jamonapi.MonitorFactory;


public class JamonProviderTest {
    private JamonProvider jamonProvider;
    @Before
    public void setup() {
        jamonProvider = new JamonProvider();
    }
    @After
    public void reset() {
        MonitorFactory.getFactory().reset();
    }
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
}
