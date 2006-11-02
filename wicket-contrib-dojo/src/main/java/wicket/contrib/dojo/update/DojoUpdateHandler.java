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

	public DojoUpdateHandler()
	{
		super();
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
	}
	
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		
		List<Component> components = new ArrayList<Component>();
		updateComponents(components, getComponent(), getComponent().getRequest().getParameter("value"));
		
		Iterator<Component> ite = components.iterator();
		
		while (ite.hasNext()){
			target.addComponent(ite.next());
		}
	}
	
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		if (getComponent() instanceof TextField){
			tag.put("onblur", "javascript:update('" + getCallbackUrl() + "&value=' + this.value" + ",'text/plain')");
		}
		else {
			tag.put("onclick", "javascript:update('" + getCallbackUrl() + "','text/plain')");
		}
	}

	/**
	 * Allow to modify a component list on value-change  
	 * @param components List of componants where a component to update should be added
	 * @param submitter components which throw the request
	 */
	public abstract void updateComponents(List<Component> components, Component submitter, String value);

    
}