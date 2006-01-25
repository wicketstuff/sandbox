package wicket.contrib.dojo.autoupdate;

import java.util.StringTokenizer;

import wicket.Application;
import wicket.AttributeModifier;
import wicket.Component;
import wicket.RequestCycle;
import wicket.Response;
import wicket.WicketRuntimeException;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.html.PackageResourceReference;
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.model.Model;
import wicket.response.StringResponse;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

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
public class DojoAutoUpdateHandler extends DojoAjaxHandler
{
	private final int interval;
	private String HTMLID;
	private String LoadingId = "";
	
	/**
	 * 
	 * @param interval The amount of milliseconds between updates and renders.
	 */
	public DojoAutoUpdateHandler(int interval)
	{
		this.interval = interval;
	    

	}
	
	public DojoAutoUpdateHandler(int interval, String loadingId)
	{
		this.interval = interval;
		this.LoadingId = loadingId;
	    

	}

    
	/**
	 * Internal render method. This handler currently only supports 1 component.<br/>
	 * The array is for future usage.
	 * 
	 * @param components The components to be rerendered.
	 * @return Rerendered component's resource stream.
	 */
	protected IResourceStream render(final Component[] components) 
	{
		
		
		StringBufferResourceStream response = new StringBufferResourceStream("text/plain");
		//response.append("<?xml version='1.0' encoding='utf-8'?>");
		//response.append("<components>");
		
		if (components != null)
		{
			
			Response resp = new StringResponse(); 
			RequestCycle requestCycle = RequestCycle.get();
			Response origResponse = requestCycle.getResponse();
			try
			{
				requestCycle.setResponse(resp);
				for (int i=0; i < components.length; i++)
				{
					//	resp.write("<component cssid=''><![CDATA[");
					
					Component component = components[i];
					boolean renderBodyOnly = component.getRenderBodyOnly();
					try
					{
						component.setRenderBodyOnly(true);

						component.doRender();
					}
					catch (Exception ex)
					{
						resp.write(ex.toString());
					}
					finally
					{
						component.setRenderBodyOnly(renderBodyOnly);
					}
				//	resp.write("]]<component>");
				}
			}
			finally
			{
				requestCycle.setResponse(origResponse);
			}
			response.append(resp.toString());
		}
		//response.append("</components>");
		//System.out.println("response: " + response.asString());
		return response;
		
	}

	/**
	 * @param container header container
	 * @see wicket.behavior.AjaxHandler#renderHeadContribution(wicket.markup.html.internal.HtmlHeaderContainer)
	 */
	protected void renderHeadContribution(HtmlHeaderContainer container)
	{
		addJsReference(container, new PackageResourceReference(Application.get(),
				DojoAutoUpdateHandler.class, "autoupdate.js"));
		
	}
	
	/* (non-Javadoc)
	 * @see wicket.AjaxHandler#getResponse()
	 */
	protected IResourceStream getResponse() 
	{
		Component[] components = new Component[1];
		components[0] = getComponent();
		boolean success = ((IUpdatable)components[0]).update();
		if(success)
		{
			return render(components);	
		}
		else
		{
			StringBufferResourceStream response = new StringBufferResourceStream("text/plain");
			response.append("UPDATE_ERROR");
			return response;
		}
			
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
	 * adds the onload contribution: calls checkUpdate() <br/>
	 * @return contribution to body onload.
	 * @see wicket.behavior.AjaxHandler#getBodyOnloadContribution()
	 */
	protected String getBodyOnloadContribution()
	{
		return "checkUpdate('" + getCallbackUrl() + "','text/plain', '" + HTMLID + "', '" + getLoadingId() + "');intervalCheck("+ this.interval + ", '" + getCallbackUrl() + "', 'text/html','" + HTMLID + "','" + getLoadingId() + "');";
		//return "checkUpdate();";
		
		
	}
	
	
	
	
	/**
	 * Checks if bound component is Updatable and adds HTMLID
	 * @see wicket.behavior.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		if (!(c instanceof IUpdatable))
		{
			throw new WicketRuntimeException("This handler must be bound to Updatable Components.");
		}
		// create a unique HTML for the explode component
		
		String componentpath = removeColon(getComponent().getPath());
		
		//this.HTMLID = "f_" + this.getComponent().getId() + "_" + getComponent().getPath();
		this.HTMLID = ((IUpdatable)getComponent()).getHTMLID();
		
		// Add ID to component, and bind effect to trigger
		c.add(new AttributeModifier("id", true, new Model(HTMLID)));

	}
	public String removeColon(String s) {
		  StringTokenizer st = new StringTokenizer(s,":",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
	  }
    
}
