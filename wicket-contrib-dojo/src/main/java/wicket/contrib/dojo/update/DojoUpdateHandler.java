package wicket.contrib.dojo.update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import wicket.Component;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.form.TextField;



public abstract class DojoUpdateHandler extends DojoAjaxHandler
{

	private Component loading;
	private String jsEvent;
	
	
	public DojoUpdateHandler(String jsEvent)
	{
		super();
		this.jsEvent = jsEvent;
	}
	
	public DojoUpdateHandler(String jsEvent, Component loading)
	{
		super();
		this.loading = loading;
		this.jsEvent = jsEvent;
	}
	
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(DojoUpdateHandler.class, "updater.js"));
	}
	

	@Override
	protected void onBind()
	{
		super.onBind();
		if (loading != null){
			loading.setOutputMarkupId(true);
		}
	}
	
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		updateComponents(target, getComponent(), getComponent().getRequest().getParameter("value"));
	}
	
	private final String getLoadingPart(){
		if (loading != null){
			return ", '" + loading.getMarkupId() + "'";
		}
		else return "";
	}
	
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		tag.put(this.jsEvent, "javascript:update('" + getCallbackUrl() + "&value=' + this.value" + ",'text/plain'" + getLoadingPart() + ")");
	}

	/**
	 * Allow to modify a component list on value-change  
	 * @param components List of componants where a component to update should be added
	 * @param submitter components which throw the request
	 */
	public abstract void updateComponents(AjaxRequestTarget target, Component submitter, String value);

    
}