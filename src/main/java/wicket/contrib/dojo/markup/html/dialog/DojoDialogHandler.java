package wicket.contrib.dojo.markup.html.dialog;

import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.html.IHeaderResponse;
import wicket.util.resource.IResourceStream;

public class DojoDialogHandler extends DojoAjaxHandler
{

	@Override
	protected IResourceStream getResponse()
	{
		return null;
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "	dojo.require(\"dojo.widget.Dialog\")\n";
		require += "\n";
		require += "function getDialog(id){\n";
		require += "	var dlg = dojo.widget.byId(id);\n";
		require += "	return dlg;\n";
		require += "}\n";
		require += "</script>\n";

		response.renderString(require);
	}

}
