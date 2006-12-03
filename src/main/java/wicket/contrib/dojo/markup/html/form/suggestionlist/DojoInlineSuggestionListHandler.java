package wicket.contrib.dojo.markup.html.form.suggestionlist;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * Handler for DojoInline suggestionList
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoInlineSuggestionListHandler extends AbstractRequireDojoBehavior
{

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#setRequire(wicket.contrib.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	@Override
	public void setRequire(RequireDojoLibs libs){
		libs.add("dojo.widget.ComboBox");		
	}

	@Override
	protected void respond(AjaxRequestTarget target){
		//DO Nothing in inline suggestionlist
	}


}
