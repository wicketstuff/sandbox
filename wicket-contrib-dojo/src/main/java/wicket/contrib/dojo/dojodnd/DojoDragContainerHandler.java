package wicket.contrib.dojo.dojodnd;

import java.util.HashMap;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

public class DojoDragContainerHandler extends AbstractRequireDojoBehavior
{
	/** container handler is attached to. */
	private DojoDragContainer container;
	
	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		this.container = (DojoDragContainer)getComponent();
	}
	
	
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}

	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(this.getClass(), "DojoDragContainerHandlerTemplate.js");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MarkupId", container.getMarkupId());
		map.put("DragId", container.getDragId());
		response.renderJavascript(template.asString(map), template.getWidgetUniqueKey(this.getComponent()));
	}


	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.dnd.*");
		libs.add("dojo.event.*");
		libs.add("dojo.io.*");
	}

}
