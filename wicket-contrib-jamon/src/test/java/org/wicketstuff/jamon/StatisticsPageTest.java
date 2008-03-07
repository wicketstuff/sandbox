package org.wicketstuff.jamon;

import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.jamon.StatisticsPage;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


public class StatisticsPageTest {
    private WicketTester wicketTester;
    
    @Before
    public void setup() {
        wicketTester = new WicketTester(StatisticsPage.class);
    }
    @After
    public void after() {
        MonitorFactory.getFactory().reset();
    }
    
    @Test
    public void shouldRenderStatisticsPageWithOneMonitor() {
        JamonTestUtil.startThisManyMonitors(1);
        Page actualPage = wicketTester.startPage(new StatisticsPage());
//        wicketTester.dumpPage();
        //TODO Assertions.
    }
    @Test
    public void shouldRenderStatisticsPageWithTwoMonitors() {
        JamonTestUtil.startThisManyMonitors(2);
        Page actualPage = wicketTester.startPage(new StatisticsPage());
//        wicketTester.dumpPage();
        //TODO Assertions
    }
}
