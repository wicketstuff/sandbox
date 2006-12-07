package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoFloatingPaneHandler extends AbstractRequireDojoBehavior {
	
	private static final String TEMPLATE = "DojoFloatingPaneHandlerTemplate.js";
	
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(this.getClass(), TEMPLATE);
		response.renderJavascript(template.asString(), template.getStaticKey());
	}

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.FloatingPane");
		
	}

}
