package wicket.contrib.dojo;

import java.util.ArrayList;
import java.util.Iterator;

import wicket.RequestCycle;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.html.IHeaderResponse;

/**
 * @author vdemay
 *
 */
public abstract class AbstractRequireDojoBehavior extends AbstractDefaultDojoBehavior
{
	private RequireDojoLibs libs = new RequireDojoLibs();
	
	/**
	 * see onComponentRendered
	 */
	private boolean once = false;
	
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractDefaultDojoBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		setRequire(libs);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		
		Iterator ite = libs.iterator();
		while(ite.hasNext()){
			require += getRequire((String)ite.next());
		}
		
		require += "\n";
		require += "</script>\n";
		
		response.renderString(require);
	}
	
	/**
	 * allow subclass to register new Dojo require libs
	 * @param libs
	 */
	public abstract void setRequire(RequireDojoLibs libs);
	
	
	private String getRequire(String lib){
		return "	dojo.require(\"" + lib + "\")\n";
	}
	
	/**
	 * this method is used to interpret dojoWidgets rendered via XMLHTTPRequest
	 */
	protected void onComponentRendered() {
		if (!once && RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			once = true;
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).appendJavascript("dojo.hostenv.makeWidgets()");
		}
	}

	
	/**
	 * @author vdemay
	 *
	 */
	public class RequireDojoLibs extends ArrayList{
		
	}

}
