package wicket.contrib.dojo;

import java.util.ArrayList;
import java.util.Iterator;

import wicket.markup.html.IHeaderResponse;

/**
 * @author vdemay
 *
 */
public abstract class AbstractRequireDojoBehavior extends AbstractDefaultDojoBehavior
{
	private RequireDojoLibs libs = new RequireDojoLibs();
	
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractDefaultDojoBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		setRequire(libs);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		
		Iterator<String> ite = libs.iterator();
		while(ite.hasNext()){
			require += getRequire(ite.next());
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
	 * @author vdemay
	 *
	 */
	public class RequireDojoLibs extends ArrayList<String>{
		
	}

}
