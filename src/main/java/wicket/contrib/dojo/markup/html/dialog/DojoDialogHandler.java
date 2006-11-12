package wicket.contrib.dojo.markup.html.dialog;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDialogHandler extends AbstractRequireDojoBehavior
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
		require += "function getDialog(id){\n";
		require += "	var dlg = dojo.widget.byId(id);\n";
		require += "	return dlg;\n";
		require += "}\n";
		require += "</script>\n";

		response.renderString(require);
	}

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.Dialog");
		
	}

}
