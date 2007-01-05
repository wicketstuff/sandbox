package wicket.contrib.dojo.markup.html;

import wicket.Component;
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
		response.renderJavascript(generateDefinition(), MaximizeBehavior.class.getName());
	}
	
	private String generateDefinition(){
		StringBuffer toReturn = new StringBuffer();
		toReturn.append("function maximize(id){\n");
		toReturn.append("	var el = dojo.byId(id);\n");
		toReturn.append("   var viewport = dojo.html.getViewport();\n");
		toReturn.append("	dojo.html.setContentBox(el, {width: viewport.width, height: viewport.height});\n");
		toReturn.append("}\n");
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
