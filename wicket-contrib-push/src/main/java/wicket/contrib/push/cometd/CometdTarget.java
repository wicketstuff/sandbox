package wicket.contrib.push.cometd;

import wicket.Component;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.push.IPushTarget;

/**
 * This merthod is a wrapper of {@link AjaxRequestTarget} on {@link IPushTarget}
 * used in {@link CometdBehavior}
 * 
 * 
 * @author Vincent Demay
 *
 */
public class CometdTarget implements IPushTarget{

	private AjaxRequestTarget target;
	
	public CometdTarget(AjaxRequestTarget target) {
		super();
		this.target = target;
	}

	public void addComponent(Component component) {
		target.addComponent(component);
	}

	public void addComponent(Component component, String markupId) {
		target.addComponent(component, markupId);
	}

	public void appendJavascript(String javascript) {
		target.appendJavascript(javascript);
	}

	public void focusComponent(Component component) {
		target.focusComponent(component);
	}

	public void prependJavascript(String javascript) {
		target.prependJavascript(javascript);
	}

}
