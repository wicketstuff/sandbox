package org.wicketstuff.jamon.webapp;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.wicketstuff.jamon.JamonTestUtil;
import org.wicketstuff.jamon.support.JamonAwareWebRequestCycleProcessor;
import org.wicketstuff.jamon.support.JamonMonitoredWebRequestCycle;
/**
 * WebApplication so Jamon Monitoring can be tested.
 * 
 * @author lars
 *
 */
public class JamonWebApplication extends WebApplication {
    public JamonWebApplication(int monitorsToStart) {
        JamonTestUtil.startThisManyMonitorsWithDelay(monitorsToStart, 15);
    }
    public JamonWebApplication() {
        this(10);
    }
    @Override
    public Class getHomePage() {
        return HomePage.class;
    }
    
    @Override
    public RequestCycle newRequestCycle(Request request, Response response) {
        return new JamonMonitoredWebRequestCycle(this, (WebRequest) request, response, true);
    }
    
    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return new JamonAwareWebRequestCycleProcessor();
    }
}
