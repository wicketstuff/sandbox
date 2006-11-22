package wicket.contrib.dojo.dojodnd;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
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
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "function byId(id){\n";
		require += "	return document.getElementById(id);\n";
		require += "}\n";
		require += "function createUrl(e){\n";
		require += "	var dragId = e.dragSource.domNode.id;\n";
		require += "	var all = e.dragSource.domNode.parentNode.getElementsByTagName('div')\n";
		require += "	var position = 0;\n";
		require += "	while (all[position] != e.dragSource.domNode){\n";
		require += "		position++;\n";
		require += "	}\n";
		require += "	\n";
		require += "	return '" + getCallbackUrl() + "' + '&dragSource=' + dragId + '&position=' + position\n";
		require += "}\n";
		require += "</script>\n";

		response.renderString(require);
		
		response.renderString(generateDropDefinition());

	}
	
	private String generateDropDefinition(){
		String toReturn = "";
		toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		toReturn += "function initDrop" + container.getMarkupId() + "(){\n";
		toReturn += "	var dl = byId(\"" + container.getId() + "\");\n";
		toReturn += "	var drop = new dojo.dnd.HtmlDropTarget(dl, [\"" + container.getDropId() + "\"]);\n";
		toReturn += "	dojo.event.connect(drop, 'onDrop', function(e) {\n";
		toReturn += "		wicketAjaxGet(createUrl(e),function(){},function(){});";
		toReturn += "	});\n";
		toReturn += "}\n";
		toReturn += "dojo.event.connect(dojo, \"loaded\", \"initDrop" + container.getMarkupId() + "\");\n";
		toReturn += "</script>\n";
		return toReturn;
	}
	
	protected void respond(AjaxRequestTarget target)
	{
		container.onAjaxModelUpdated(target);
	}

	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.dnd.*");
		libs.add("dojo.event.*");
		libs.add("dojo.io.*");
	}
	


}
