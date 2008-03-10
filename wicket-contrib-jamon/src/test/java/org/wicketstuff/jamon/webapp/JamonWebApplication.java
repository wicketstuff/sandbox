package org.wicketstuff.jamon.webapp;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.wicketstuff.jamon.JamonTestUtil;
import org.wicketstuff.jamon.support.JamonMonitoredWebRequestCycle;
/**
 * WebApplication so the statistics can be tested.
 * @author lars
 *
 */
public class JamonWebApplication extends WebApplication {
    public JamonWebApplication() {
        JamonTestUtil.startThisManyMonitorsWithDelay(10, 15);
    }
    @Override
    public Class getHomePage() {
        return HomePage.class;
    }
    
    @Override
    public RequestCycle newRequestCycle(Request request, Response response) {
        return new JamonMonitoredWebRequestCycle(this, (WebRequest) request, response);
    }
}
