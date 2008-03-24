package org.wicketstuff.jamon;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
/**
 * Util class for Jamon dependant tests.
 * 
 * @author lars
 *
 */
public class JamonTestUtil {
    public static final String MONITOR_PREFIX = "mon";
    public static void startThisManyMonitors(int numberOfMonitorsToStart) {
        startThisManyMonitorsWithDelay(numberOfMonitorsToStart, 0l);
    }
    public static void startThisManyMonitorsWithDelay(int numberOfMonitorsToStart, long delay) {
        for (int i = 0; i < numberOfMonitorsToStart; i++) {
            Monitor monitor = MonitorFactory.getFactory().start(MONITOR_PREFIX+i);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                //test code so just ignore. :-/
            }
            monitor.stop();
        }
    }

}
