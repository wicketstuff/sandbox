package org.wicketstuff.jamon.support;

import org.apache.wicket.Page;
import org.apache.wicket.RequestListenerInterface;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.IBehaviorListener;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.request.target.component.listener.BehaviorRequestTarget;
import org.apache.wicket.util.tester.WicketTester;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.jamon.webapp.AjaxPage;
import org.wicketstuff.jamon.webapp.HomePage;
import org.wicketstuff.jamon.webapp.JamonWebApplication;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorComposite;
import com.jamonapi.MonitorFactory;


public class JamonMonitoredWebRequestCycleTest {
    
    private static class CustomWicketTester extends WicketTester {
        public CustomWicketTester(WebApplication webApplication) {
            super(webApplication);
        }
        
        public Page openPage(Class<? extends Page> pageClass) {
            WebRequestCycle requestCycle = setupRequestAndResponse();
            requestCycle.getRequest().getRequestParameters().setBookmarkablePageClass(pageClass.getName());
            processRequestCycle(requestCycle);
            return getLastRenderedPage();
        }
        
        public void clickAjaxLink(String linkPath) {
            WebRequestCycle cycle = setupRequestAndResponse();
            cycle.getRequest().getRequestParameters().setInterfaceName(IBehaviorListener.class.getSimpleName());
            cycle.getRequest().getRequestParameters().setBehaviorId("0");
            cycle.getRequest().getRequestParameters().setComponentPath(getComponentFromLastRenderedPage(linkPath).getPath());
            processRequestCycle(cycle);
        }
        
    }
    
    private CustomWicketTester wicketTester;
    
    private JamonWebApplication jamonWebApplication;
    
    
    @Before
    public void setup() {
        jamonWebApplication = new JamonWebApplication();
        wicketTester = new CustomWicketTester(jamonWebApplication);
        MonitorFactory.getFactory().reset();
    }
    
    @Test
    public void shouldCreateMonitorForPagesThatAreNavigatedTo() {
        wicketTester.openPage(HomePage.class);
        
        assertEquals(1, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);
    }
    
    @Test
    public void shouldCreateMonitorIfAjaxLinkIsClickedOnPage() {
        wicketTester.openPage(AjaxPage.class);

        wicketTester.clickAjaxLink("ajaxLink");
        wicketTester.clickAjaxLink("ajaxLink");
        assertEquals(2, MonitorFactory.getMonitor("AjaxPage.ajaxLink -> AjaxPage", "ms.").getHits(), 0);
        
    }
    @Test
    public void proofThatClickLinkInWicketTesterDoesNotWorkAsExpected() {
        wicketTester.openPage(AjaxPage.class);
        wicketTester.clickLink("ajaxLink");
        
        assertEquals(0, MonitorFactory.getMonitor("AjaxPage.ajaxLink -> AjaxPage", "ms.").getHits(), 0);
    }
    @Test
    public void proofThatStartPageWithClassDoesNotWorkAsExpected() {
        wicketTester.startPage(HomePage.class);
        
        assertEquals(0, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);
        
    }
    @Test
    public void proofThatStartPageWithPageDoesNotWorkAsExpected() {
        wicketTester.startPage(new HomePage());
        
        assertEquals(0, MonitorFactory.getMonitor("HomePage", "ms.").getHits(), 0);
    }
}
