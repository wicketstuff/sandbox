package org.wicketstuff.jamon.support;

import org.apache.wicket.Page;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * {@link WebRequestCycle} that will add a monitor for all pages that are rendered. The labels
 * of the {@link Monitor}s of the {@link Page}s are the fully qualified classnames.
 * Users should return an instance of this class in 
 * {@link WebApplication#newRequestCycle(org.apache.wicket.Request, Response)} method.
 * 
 * @author lars
 *
 */
public class JamonMonitoredWebRequestCycle extends WebRequestCycle {
    public JamonMonitoredWebRequestCycle(WebApplication application, WebRequest request, Response response) {
        super(application, request, response);
        this.startTime = 0;
    }

    static final String UNIT = "ms.";
    
    private long startTime;
 
    @Override
    protected void onBeginRequest() {
        super.onBeginRequest();
        startTime = System.currentTimeMillis();
    }
 
    @Override
    protected void onEndRequest() {
        super.onEndRequest();
        calculateDurationAndAddToMonitor();
    }
 
    private void calculateDurationAndAddToMonitor() {
        if(startTime != 0) {
            Class<?> pageClass = null;
            if(getWebResponse().isAjax() && getWebRequest().getPage() != null) {
                pageClass = getWebRequest().getPage().getClass();
            } else {
                pageClass = getResponsePageClass();
            }
 
            if(pageClass != null) {
                MonitorFactory.add(pageClass.getCanonicalName(), UNIT,
                                          System.currentTimeMillis() - startTime);
            }
        }
    }
}
