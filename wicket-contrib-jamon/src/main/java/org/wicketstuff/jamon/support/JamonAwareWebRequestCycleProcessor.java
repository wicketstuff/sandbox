package org.wicketstuff.jamon.support;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.component.IBookmarkablePageRequestTarget;
import org.apache.wicket.request.target.component.IPageRequestTarget;
import org.apache.wicket.request.target.component.listener.IListenerInterfaceRequestTarget;


/**
 * {@link WebRequestCycleProcessor} implementation that will get the source from which a request
 * originated and the target it will resolve to. These are needed by the
 * {@link JamonMonitoredWebRequestCycle} to determine the monitor's label.
 * 
 * This class can only be used in combination with {@link JamonMonitoredWebRequestCycle} otherwise
 * {@link IllegalStateException}s will be thrown.
 * 
 * @author lvonk
 * 
 */
public class JamonAwareWebRequestCycleProcessor extends WebRequestCycleProcessor {

    private static final String DELIMETER = ":";

    @Override
    public IRequestTarget resolve(RequestCycle requestCycle, RequestParameters requestParameters) {
        IRequestTarget target = super.resolve(requestCycle, requestParameters);
        JamonMonitoredWebRequestCycle jamonMonitoredWebRequestCycle = castToJamonMonitoredWebRequestCycle(requestCycle);
        resolveSourceLabel(target, jamonMonitoredWebRequestCycle);
        return target;
    }

    @Override
    public void respond(RequestCycle requestCycle) {
        JamonMonitoredWebRequestCycle jamonMonitoredWebRequestCycle = castToJamonMonitoredWebRequestCycle(requestCycle);
        // this is the last request target.
        IRequestTarget target = jamonMonitoredWebRequestCycle.getRequestTarget();
        resolveTargetLabel(target, jamonMonitoredWebRequestCycle);
        super.respond(requestCycle);
    }

    /*
     * Resolves the source label. This is where the request originated from. This can be a link,
     * direct page access checkbox etc. The source label is then setup upon the given cycle in the
     * for of: PageClassName.componentId.
     */
    private void resolveSourceLabel(IRequestTarget requestTarget, JamonMonitoredWebRequestCycle cycle) {
        if (requestTarget instanceof IBookmarkablePageRequestTarget) {
            IBookmarkablePageRequestTarget target = (IBookmarkablePageRequestTarget) requestTarget;
            cycle.setSource(target.getPageClass().getSimpleName());
        } else if (requestTarget instanceof IListenerInterfaceRequestTarget) {
            IListenerInterfaceRequestTarget target = (IListenerInterfaceRequestTarget) requestTarget;
            cycle.setSource(target.getPage().getClass().getSimpleName() + "." + getRelativePath(target));
        }
    }

    /*
     * returns the relative path with a max of 3. This to prevent extremely long names. My guess is
     * that three is sufficient for determining which link was clicked.
     */
    private String getRelativePath(IListenerInterfaceRequestTarget target) {

        String relativePath = target.getTarget().getPageRelativePath();
        String[] parts = relativePath.split(DELIMETER);
        if (parts.length > 3) {
            relativePath = new StringBuilder(parts[parts.length - 3]).append(DELIMETER).append(parts[parts.length - 2]).append(DELIMETER).append(
                    parts[parts.length - 1]).toString();
        }
        return relativePath;
    }

    /*
     * Resolves the target label. This is where the request resolves to. In all cases this will be
     * the name of the page class that is (partially in case of Ajax) rendered.
     */
    private void resolveTargetLabel(IRequestTarget requestTarget, JamonMonitoredWebRequestCycle cycle) {
        if (requestTarget instanceof IBookmarkablePageRequestTarget) {
            IBookmarkablePageRequestTarget target = (IBookmarkablePageRequestTarget) requestTarget;
            cycle.setTarget(target.getPageClass().getSimpleName());
        } else if (requestTarget instanceof IPageRequestTarget) {
            IPageRequestTarget target = (IPageRequestTarget) requestTarget;
            cycle.setTarget(target.getPage().getClass().getSimpleName());
        }
    }

    private JamonMonitoredWebRequestCycle castToJamonMonitoredWebRequestCycle(RequestCycle requestCycle) {
        if (!(requestCycle instanceof JamonMonitoredWebRequestCycle)) {
            throw new IllegalStateException(
                    "You can only use the JamonAwareWebRequestCycleProcessor in combination with the JamonMonitoredWebRequestCycle");
        }
        return (JamonMonitoredWebRequestCycle) requestCycle;
    }
}
