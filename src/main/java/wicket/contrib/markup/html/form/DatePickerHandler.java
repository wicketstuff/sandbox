package wicket.contrib.markup.html.form;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * A dojo date picker
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DatePickerHandler extends AbstractDefaultDojoBehavior
{
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "	dojo.require(\"dojo.widget.DropdownDatePicker\")\n";
		require += "\n";
		require += "</script>\n";

		response.renderString(require);
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING	
	}

}
