package org.wicketstuff.dojo.dojodnd;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 * A Handler associated to {@link DojoDragContainer}
 * </p>
 * 
 * @author Vincent Demay
 *
 */

@SuppressWarnings("serial")
public class DojoDragContainerHandler extends AbstractDojoDragContainerHandler
{
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		DojoDragContainer container = getDojoDragContainer();
		
		//DojoOnLoad only if not AjaxRequest
		IRequestTarget target = RequestCycle.get().getRequestTarget();
		if(!(target instanceof AjaxRequestTarget)){
			response.renderJavascript("dojo.event.connect(dojo, \"loaded\", function() {" + 
					"new wicketstuff.dojodnd.DojoDragContainer('" + container.getMarkupId() + "','" + container.getDragPattern() + "').initializeDragContainer()});\n", 
					container.getMarkupId() + "onLoad" );
		}
		//else will be done by onComponentReRendered
	}
	
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget)
	{
		super.onComponentReRendered(ajaxTarget);
		
		DojoDragContainer container = getDojoDragContainer();
		ajaxTarget.appendJavascript("new wicketstuff.dojodnd.DojoDragContainer('" + container.getMarkupId() + "','" + container.getDragPattern() + "').initializeDragContainer();\n");
	}
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.dojo.dojodnd.AbstractDojoDragContainerHandler#setRequire(org.wicketstuff.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	@Override
	public void setRequire(RequireDojoLibs libs) {
		super.setRequire(libs);
		
		libs.add("wicketstuff.dojodnd.DojoDragContainer");
	}
}
