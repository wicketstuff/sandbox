package wicket.contrib.dojo.markup.html.form;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * A dojo date picker
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDatePickerHandler extends AbstractRequireDojoBehavior
{
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING	
	}

	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.DropdownDatePicker");		
	}

}
