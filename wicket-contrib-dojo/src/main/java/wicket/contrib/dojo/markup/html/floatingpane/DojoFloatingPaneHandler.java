package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoFloatingPaneHandler extends AbstractDefaultDojoBehavior
{
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
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "	dojo.require(\"dojo.widget.FloatingPane\")\n";
		require += "\n";
		require += "function getFloatingPane(id){\n";
		require += "	var fp = dojo.widget.byId(id);\n";
		require += "	return fp;\n";
		require += "}\n";
		require += "</script>\n";

		response.renderString(require);
	}

}
