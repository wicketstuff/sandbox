package wicket.contrib.dojo.dojodnd;

import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.html.IHeaderResponse;
import wicket.util.resource.IResourceStream;

public class DojoDragContainerHandler extends DojoAjaxHandler
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
	protected IResourceStream getResponse()
	{
		return null;
	}

	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "	dojo.require(\"dojo.dnd.*\")\n";
		require += "	dojo.require(\"dojo.event.*\")\n";
		require += "	dojo.require(\"dojo.io.*\")\n";
		require += "\n";
		require += "function byId(id){\n";
		require += "	return document.getElementById(id);\n";
		require += "}\n";
		require += "</script>\n";

		response.renderString(require);

		response.renderString(generateDragDefinition());
	}
	
	private String generateDragDefinition(){
		String toReturn = "";
		toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		toReturn += "function initDrag" + container.getMarkupId() + "(){\n";
		toReturn += "	var dl = byId(\"" + container.getMarkupId() + "\");\n";
		toReturn += "	var drag = new dojo.dnd.HtmlDragSource(dl, \"" + container.getDragId() + "\");\n";
		toReturn += "}\n";
		toReturn += "dojo.event.connect(dojo, \"loaded\", \"initDrag" + container.getMarkupId() + "\");\n";
		toReturn += "</script>\n";
		return toReturn;
	}

}
