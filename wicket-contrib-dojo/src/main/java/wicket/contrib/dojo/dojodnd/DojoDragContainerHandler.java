package wicket.contrib.dojo.dojodnd;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * <p>
 * A Handler associated to {@link DojoDragContainer}
 * </p>
 * 
 * @author Vincent Demay
 *
 */
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
		response.renderString(generateDragDefinition());
	}
	
	private String generateDragDefinition(){
		String toReturn = "";
		toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		toReturn += "function initDrag" + container.getMarkupId() + "(){\n";
		toReturn += "	var dl = byId(\"" + container.getMarkupId() + "\");\n";
		toReturn += "	var drag = new dojo.dnd.HtmlDragSource(dl, \"" + container.getDragPattern() + "\");\n";
		toReturn += "}\n";
		toReturn += "dojo.event.connect(dojo, \"loaded\", \"initDrag" + container.getMarkupId() + "\");\n";
		toReturn += "</script>\n";
		return toReturn;
	}


	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.dnd.*");
		libs.add("dojo.event.*");
		libs.add("dojo.io.*");
	}

}
