package wicket.contrib.dojo.markup.html;

import wicket.Component;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * Dojo behaviour that maximizes an HTML element to the whole window's viewport size
 */
public class MaximizeBehavior extends AbstractRequireDojoBehavior
{
	/** container handler is attached to. */
	private Component container;
	
	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		this.container = getComponent();
	}
	
	/**
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(getClass(), "MaximizeBehavior.js"));
		response.renderJavascript(generateDefinition(), MaximizeBehavior.class.getName() + container.getMarkupId());
	}
	
	private String generateDefinition(){
		StringBuffer toReturn = new StringBuffer();
		toReturn.append("dojo.addOnLoad( function() { maximize('" + container.getMarkupId() + "'); });\n");
		return toReturn.toString();
	}
	
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.html");
	}

	protected void respond(AjaxRequestTarget target)
	{
		// Not used
	}
}
