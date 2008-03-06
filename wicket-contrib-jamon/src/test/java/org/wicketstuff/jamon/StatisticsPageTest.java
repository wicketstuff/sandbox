package org.wicketstuff.jamon;

import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
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
    
    @Test
    public void shouldRenderStatisticsPageWithOneMonitor() {
        Monitor monitor = MonitorFactory.getFactory().start("test1");

        monitor.stop();
        Page actualPage = wicketTester.startPage(new StatisticsPage());
//        wicketTester.dumpPage();
        //TODO Assertions.
    }
    @Test
    public void shouldRenderStatisticsPageWithTwoMonitors() {
        Monitor monitor = MonitorFactory.getFactory().start("test1");
        monitor.stop();
        Monitor monitor2 = MonitorFactory.getFactory().start("test2");
        monitor2.stop();
        Page actualPage = wicketTester.startPage(new StatisticsPage());
//        wicketTester.dumpPage();
        //TODO Assertions
    }
}
