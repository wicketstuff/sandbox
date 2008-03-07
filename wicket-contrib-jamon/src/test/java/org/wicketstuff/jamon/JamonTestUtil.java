package org.wicketstuff.jamon;

import com.jamonapi.MonitorFactory;
/**
 * Util class for Jamon dependant tests.
 * 
 * @author lars
 *
 */
class JamonTestUtil {
    public static void startThisManyMonitors(int numberOfMonitorsToStart) {
        for (int i = 0; i < numberOfMonitorsToStart; i++) {
            MonitorFactory.getFactory().start("mon"+i).stop();
        }
    }

}
