package org.wicketstuff.jamon.support;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.component.IBookmarkablePageRequestTarget;
import org.apache.wicket.request.target.component.IPageRequestTarget;
import org.apache.wicket.request.target.component.listener.IListenerInterfaceRequestTarget;
import org.apache.wicket.request.target.component.listener.RedirectPageRequestTarget;


/**
 * <p>
 * To use this class in your {@link WebApplication} simply override the {@link WebApplication#newRequestCycleProcessor} 
 * method in your own {@link WebApplication}.
 * </p>
 * <p>
 * This {@link WebRequestCycleProcessor} implementation that will get the source from where a request
 * originated from and the target to where it will resolve to. These are needed by the
 * {@link JamonMonitoredWebRequestCycle} to determine the monitor's label.
 * <br>
 * This class can only be used in combination with {@link JamonMonitoredWebRequestCycle} otherwise
 * an {@link IllegalStateException} will be thrown.
 * </p>
 * <p>
 * <b>Implementation limitations:</b>
 * <br>
 * Only if the {@link WebRequestCycle} comes from an {@link IBookmarkablePageRequestTarget} or an
 * {@link IListenerInterfaceRequestTarget} <i>and</i> the eventual target of the {@link WebRequestCycle} is
 * an {@link IPageRequestTarget} or an {@link IBookmarkablePageRequestTarget} the Monitors are created.
 * If you want to support more types of targets you can extend this class and implement the methods
 * {@link #doResolveSourceLabel(IRequestTarget, JamonMonitoredWebRequestCycle)} 
 * and {@link #doResolveTargetLabel(IRequestTarget, JamonMonitoredWebRequestCycle)}.
 * </p>
 * 
 * @author lars
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
    /**
     * Subclasses should implement this method if they want to monitor more types of {@link IRequestTarget}s
     * than this {@link JamonAwareWebRequestCycleProcessor} supports. See {@link JamonAwareWebRequestCycleProcessor} javadoc for
     * the currently supported types. Besides this method subclasses should also consider implementing 
     * {@link #doResolveTargetLabel(IRequestTarget, JamonMonitoredWebRequestCycle)}.
     * <br>
     * Subclasses should at least call the following methods on the given {@link JamonMonitoredWebRequestCycle}:
     * <br>
     * <ul>
     * <li>{@link JamonMonitoredWebRequestCycle#comesFromPage(Class)}</li>
     * <li>{@link JamonMonitoredWebRequestCycle#setSource(String)}</li>
     * </ul>
     * <br>
     * The default implementation of this method does nothing.
     * 
     * @param requestTarget The request target of where the request originated from. 
     * @param cycle The {@link JamonMonitoredWebRequestCycle}.
     */
    protected void doResolveSourceLabel(IRequestTarget requestTarget, JamonMonitoredWebRequestCycle cycle) {
    }
    /**
     * Subclasses should implement this method if they want to monitor more types of {@link IRequestTarget}s
     * than this {@link JamonAwareWebRequestCycleProcessor} supports. See {@link JamonAwareWebRequestCycleProcessor} javadoc for
     * the currently supported types. Besides this method subclasses should also consider implementing 
     * {@link #doResolveSourceLabel(IRequestTarget, JamonMonitoredWebRequestCycle)}.
     * <br>
     * Subclasses should at least call the method {@link JamonMonitoredWebRequestCycle#setTarget(Class)} 
     * on the given {@link JamonMonitoredWebRequestCycle}.
     * <br>
     * The default implementation of this method does nothing.
     * 
     * @param requestTarget The request target of where the request will resolve to. 
     * @param cycle The {@link JamonMonitoredWebRequestCycle}.
     */
    protected void doResolveTargetLabel(IRequestTarget requestTarget, JamonMonitoredWebRequestCycle cycle) {
    }
    /*
     * Resolves the source label. This is where the request originated from. This can be a link,
     * direct page access, checkbox etc. The source label is then setup upon the given cycle in the
     * for of: PageClassName.componentId.
     */
    private void resolveSourceLabel(IRequestTarget requestTarget, JamonMonitoredWebRequestCycle cycle) {
        if (requestTarget instanceof IBookmarkablePageRequestTarget) {
            IBookmarkablePageRequestTarget target = (IBookmarkablePageRequestTarget) requestTarget;
            cycle.comesFromPage(target.getPageClass());
            cycle.setSource(target.getPageClass().getSimpleName());
        } else if (requestTarget instanceof IListenerInterfaceRequestTarget) {
            IListenerInterfaceRequestTarget target = (IListenerInterfaceRequestTarget) requestTarget;
            Class<? extends Page> pageClass = target.getPage().getClass();
            cycle.comesFromPage(pageClass);
            String source = addComponentNameToLabelIfNotRedirectPageRequestTarget(target, pageClass.getSimpleName());
            cycle.setSource(source);
        } else {
            doResolveSourceLabel(requestTarget, cycle);
        }
    }

    private String addComponentNameToLabelIfNotRedirectPageRequestTarget(IListenerInterfaceRequestTarget target, String source) {
        if(!(target instanceof RedirectPageRequestTarget)) {
            source += "." + getRelativePath(target);
        }
        return source;
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
            Class<? extends Page> pageClass = target.getPageClass();
            cycle.setTarget(pageClass);
        } else if (requestTarget instanceof IPageRequestTarget) {
            IPageRequestTarget target = (IPageRequestTarget) requestTarget;
            Class<? extends Page> pageClass = target.getPage().getClass();
            cycle.setTarget(pageClass);
        } else {
            doResolveTargetLabel(requestTarget, cycle);
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
