package org.wicketstuff.jamon.webapp;

import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jamon.JamonTestUtil;
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
}
