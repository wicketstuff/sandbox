package wicket.contrib.dojo.autoupdate;

import wicket.Component;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.html.IHeaderResponse;

/**
 * Dojo Ajax auto update handler. <br/>
 * Bind this handler to any component implementing IUpdatable.<br/>
 * Every <i>interval</i> the bound component's update() method will be called<br/>
 * followed by a rerender of the bound component.<br/>
 * 
 * @author Ruud Booltink
 * @author Marco van de Haar
 *
 */
public abstract class DojoAutoUpdateHandler extends DojoAjaxHandler
{
	private final int interval;
	private String LoadingId = "";
	
	/**
	 * 
	 * @param interval The amount of milliseconds between updates and renders.
	 */
	public DojoAutoUpdateHandler(int interval)
	{
		this.interval = interval;
		//getComponent().setOutputMarkupId(true);
	}
	
	/**
	 * @param interval
	 * @param loadingId
	 */
	public DojoAutoUpdateHandler(int interval, String loadingId)
	{
		this.interval = interval;
		this.LoadingId = loadingId;
	}

	/**
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(DojoAutoUpdateHandler.class, "autoupdate.js"));
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "\n";
		require += "function initAutoRefreshFor" + getComponent().getId() + "(){\n";
		require += "	checkUpdate('" + getCallbackUrl() + "','text/plain', '" + getComponent().getId() + "', '" + getLoadingId() + "');intervalCheck("+ this.interval + ", '" + getCallbackUrl() + "', 'text/html','" + getComponent().getId() + "','" + getLoadingId() + "');\n";
		require += "}\n";
		require += "dojo.addOnLoad(initAutoRefreshFor" + getComponent().getId() + ");\n";
		require += "</script>\n";
		
		response.renderString(require);
	}
	
	
	/**
	 * the javascript function expects the node corresponding to loadingId to have 
	 * a style attribute: visibility=hidden; and sets it to visible during laoding.
	 * This is not very good method for doing this and should probably be replaced by
	 *  a a generic js function which is called during loading.
	 * @return the CSS id for the loading node.
	 */
	protected String getLoadingId()
	{
		return this.LoadingId;
	}
	
	/**
	 * Checks if bound component is Updatable and adds HTMLID
	 * @see wicket.behavior.AjaxHandler#onBind()
	 */
	/*protected void onBind()
	{
		Component c = getComponent();
	}*/
	
	/*private String removeColon(String s) {
		  StringTokenizer st = new StringTokenizer(s,":",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
	  }*/

	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		update(getComponent());
		target.addComponent(getComponent());
	}
	
	/**
	 * update the component where this handler is added
	 * @param component component where this handler is added
	 */
	protected abstract void update(Component component);
    
}
