package wicket.contrib.dojo.dojodnd;

import java.util.HashMap;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

/**
 * Package class for Dojo DropContainer
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
class DojoDropContainerHandler extends AbstractRequireDojoBehavior
{
	/** container handler is attached to. */
	private DojoDropContainer container;


	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		this.container = (DojoDropContainer)getComponent();
	}
	
	/**
	 * 
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(this.getClass(), "DojoDropContainerHandlerTemplate.js");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MarkupId", container.getMarkupId());
		map.put("DropId", container.getDropId());
		map.put("CallbackUrl", getCallbackUrl());
		map.put("Id", container.getId());
		response.renderJavascript(template.asString(map), template.getWidgetUniqueKey(this.getComponent()));

	}
	
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		container.onAjaxModelUpdated(target);
	}

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.dnd.*");
		libs.add("dojo.event.*");
		libs.add("dojo.io.*");
	}
	


}
