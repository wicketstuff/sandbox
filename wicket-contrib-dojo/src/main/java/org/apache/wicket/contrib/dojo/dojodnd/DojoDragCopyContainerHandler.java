package org.apache.wicket.contrib.dojo.dojodnd;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 * A Handler associated to {@link DojoDragCopyContainer}
 * </p>
 * 
 * @author B. Molenkamp
 * @version SVN: $Id$
 * 
 */
@SuppressWarnings("serial")
public class DojoDragCopyContainerHandler extends AbstractDojoDragContainerHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(this.getClass(), "DojoDragCopyContainerHandlerTemplate.js");
		response.renderJavascript(template.asString(), template.getStaticKey());

		// DojoOnLoad only if not AjaxRequest
		IRequestTarget target = RequestCycle.get().getRequestTarget();
		if (!(target instanceof AjaxRequestTarget)) {
			DojoDragCopyContainer container = getDojoDragCopyContainer();
			response.renderJavascript(
					"dojo.event.connect(dojo, \"loaded\", function() {initDragCopy('" + container.getMarkupId() + "','" + container.getDragPattern() + "', " + container.isCopyOnce() + ")});\n", 
					container.getMarkupId() + "onLoad");
		}
		// else will be done by onComponentReRendered
	}

	public void onComponentReRendered(AjaxRequestTarget ajaxTarget) {
		super.onComponentReRendered(ajaxTarget);
		
		DojoDragCopyContainer container = getDojoDragCopyContainer();
		ajaxTarget.appendJavascript("initDragCopy('" + container.getMarkupId() + "','" + container.getDragPattern() + "', " + container.isCopyOnce() + ")\n");
	}
	
	/**
	 * Returns the
	 * @return
	 */
	protected DojoDragCopyContainer getDojoDragCopyContainer() {
		return (DojoDragCopyContainer) getDojoDragContainer();
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.dojodnd.AbstractDojoDragContainerHandler#setRequire(wicket.contrib.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setRequire(RequireDojoLibs libs) {
		super.setRequire(libs);
		libs.add("dojo.dnd.HtmlDragCopy");
	}
}
