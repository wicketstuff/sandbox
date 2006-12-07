package wicket.contrib.dojo.markup.html.dialog;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDialogHandler extends AbstractRequireDojoBehavior
{
	private final static String TEMPLATE = "DojoDialogHandlerTemplate.js";
	
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
		libs.add("dojo.widget.Dialog");
		
	}

}
