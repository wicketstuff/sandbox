package wicket.contrib.dojo.markup.html.form.suggestionlist;

import wicket.Page;
import wicket.RequestCycle;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;
import wicket.request.target.basic.StringRequestTarget;

/**
 * Handler for {@link DojoRemoteSuggestionList}
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoRemoteSuggestionListHandler extends AbstractAjaxBehavior{

	/* (non-Javadoc)
	 * @see wicket.behavior.AbstractAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response){
		super.renderHead(response);
		response.renderJavascriptReference(AbstractDefaultDojoBehavior.DOJO);
		
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "	dojo.require('dojo.widget.ComboBox')\n";
		require += "\n";
		require += "</script>\n";
		
		response.renderString(require);
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag){
		super.onComponentTag(tag);
		tag.put("dataUrl", getCallbackUrl() + "&search=%{searchString}");
		tag.put("mode","remote");
	}
	
	/**
	 * @see wicket.behavior.IBehaviorListener#onRequest()
	 */
	public final void onRequest()
	{
		boolean isPageVersioned = true;
		Page page = getComponent().getPage();
		try
		{
			isPageVersioned = page.isVersioned();
			page.setVersioned(false);

			String response = respond();
			
			StringRequestTarget target = new StringRequestTarget(response);
			RequestCycle.get().setRequestTarget(target);
		}
		finally
		{
			page.setVersioned(isPageVersioned);
		}
	}

	protected String respond(){
		String pattern = getComponent().getRequest().getParameter("search");
		SuggestionList list = ((DojoRemoteSuggestionList)getComponent()).getMatchingValues(pattern);
		return list.getJson();
	}

	

}
