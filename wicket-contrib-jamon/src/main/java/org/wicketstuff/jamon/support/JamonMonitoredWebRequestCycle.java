package org.wicketstuff.jamon.support;

import org.apache.wicket.Page;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.jamon.web.JamonAdminPage;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;


/**
 * <p>
 * To use this class simply override the method {@link WebApplication#newRequestCycle(org.apache.wicket.Request, Response)}
 * in your {@link WebApplication}. You can only use this class in combination with the {@link JamonAwareWebRequestCycleProcessor}.
 * </p>
 * <p>
 * The responsibility of the {@link JamonMonitoredWebRequestCycle} is to add a monitor for all actions that will cause pages 
 * or parts of pages to be rendered. <br>
 * The labels of the {@link Monitor}s come in these formats:
 * 
 * <ul>
 * <li>
 * "PageName" - When a user navigates directly to a Page, for instance the HomePage, or a bookmarked Page.
 * </li>
 * <li>
 * "PageName.toNextPage -> NextPage" - When the component toNextPage on the PageName page causes the user to navigate to the "NextPage" page <b>and</b>
 * this {@link JamonMonitoredWebRequestCycle} has its property {@link #includeSourceNameInMonitorLabel} set to <code>true</code>.
 * </li>
 * <li>
 * "NextPage" - When the user navigates to the "NextPage" page from whatever page he was on <b>and</b>
 * this {@link JamonMonitoredWebRequestCycle} has its property {@link #includeSourceNameInMonitorLabel} set to <code>false</code>.
 * </li>
 * </ul>
 * 
 * Any navigations from or to the {@link JamonAdminPage} is excluded from the Monitors.
 * </p>
 * @author lars
 * 
 */
public class JamonMonitoredWebRequestCycle extends WebRequestCycle {

    static final String UNIT = "ms.";

    /**
     * At what time did the request start.
     */
    private long startTimeRequest;

    /**
     * The source from where the request originated. This will typically be in the form of
     * PageName.component. Where component is the name of the component that was clicked.
     * 
     */
    private String source;

    /**
     * The name of the target object that was rendered. This will be the page name in most cases.
     */
    private String target;

    /**
     * Should should the source name be included in the Monitors.
     */
    private final boolean includeSourceNameInMonitorLabel;

    /**
     * Should we ignore this request cycle as it involves the {@link JamonAdminPage}
     * itself?
     */
    private boolean dontMonitorThisRequest;

    /**
     * Constructs a {@link JamonMonitoredWebRequestCycle} that will <b>not</b> use the {@link #source}
     * in the Monitors label.
     * 
     * @param application The {@link WebApplication} that will be monitored by Jamon.
     * @param request The {@link WebRequest}.
     * @param response The {@link Response}.
     */
    public JamonMonitoredWebRequestCycle(WebApplication application, WebRequest request, Response response) {
        this(application, request, response, false);
    }

    /**
     * Construct.
     * 
     * @param application The {@link WebApplication} that will be monitored by Jamon.
     * @param request The {@link WebRequest}.
     * @param response The {@link Response}.
     * @param includeSourceNameInMonitorLabel whether or not to include the name of the {@link #source} in the Monitors label.  
     */
    public JamonMonitoredWebRequestCycle(WebApplication application, WebRequest request, Response response, boolean includeSourceNameInMonitorLabel) {
        super(application, request, response);
        this.includeSourceNameInMonitorLabel = includeSourceNameInMonitorLabel;
        this.startTimeRequest = 0;
    }

    
    @Override
    protected void onBeginRequest() {
        this.startTimeRequest = System.currentTimeMillis();
        super.onBeginRequest();
    }

    @Override
    protected void onEndRequest() {
        super.onEndRequest();
        calculateDurationAndAddToMonitor();
    }

    /**
     * Sets the {@link #source}.
     * 
     * @param source The name of the source where the request originated from.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Set the {@link #target}.
     * 
     * @param destination The name of the page that was rendered.
     */
    public void setTarget(Class<? extends Page> destination) {
        this.target = destination.getSimpleName();
        this.dontMonitorThisRequest = (JamonAdminPage.class.isAssignableFrom(destination));
    }
    
    /**
     * From which Page did the request come from? This is needed for creating the Monitor label.
     * @param clazz
     */
    public void comesFromPage(Class<? extends Page> clazz) {
        this.dontMonitorThisRequest = (JamonAdminPage.class.isAssignableFrom(clazz)); 
    }

    private void calculateDurationAndAddToMonitor() {
        if (this.startTimeRequest != 0 && !dontMonitorThisRequest) {
            long duration = System.currentTimeMillis() - startTimeRequest;
            if (includeSourceNameInMonitorLabel) {
                MonitorFactory.add(createLabel(), UNIT, duration);
            } else {
                MonitorFactory.add(String.format("%s", target), UNIT, duration);
            }
        }
    }

    private String createLabel() {
        if (source == null || source.equals(target)) {
            return String.format("%s", target);
        } else {
            return String.format("%s -> %s", source, target);
        }
    }
}
