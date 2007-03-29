package wicket.contrib.dojo.dojodnd;

import wicket.IRequestTarget;
import wicket.RequestCycle;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

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
		
		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(this.getClass(), "DojoDragContainerHandlerTemplate.js");
		response.renderJavascript(template.asString(), template.getStaticKey());
		
		//DojoOnLoad only if not AjaxRequest
		IRequestTarget target = RequestCycle.get().getRequestTarget();
		if(!(target instanceof AjaxRequestTarget)){
			response.renderJavascript("dojo.event.connect(dojo, \"loaded\", function() {initDrag('" + container.getMarkupId() + "','" + container.getDragPattern() + "')});\n", container.getMarkupId() + "onLoad" );
		}
		//else will be done by onComponentReRendered
	}
	
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget)
	{
		super.onComponentReRendered(ajaxTarget);
		
		DojoDragContainer container = getDojoDragContainer();
		ajaxTarget.appendJavascript("initDrag('" + container.getMarkupId() + "','" + container.getDragPattern() + "')\n");
	}
}
