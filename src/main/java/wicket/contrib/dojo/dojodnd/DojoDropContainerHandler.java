package wicket.contrib.dojo.dojodnd;

import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.html.IHeaderResponse;
import wicket.util.resource.IResourceStream;

/**
 * Package class for Dojo DropContainer
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
class DojoDropContainerHandler extends DojoAjaxHandler
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
		require += "	dojo.require(\"dojo.dnd.*\")\n";
		require += "	dojo.require(\"dojo.event.*\")\n";
		require += "	dojo.require(\"dojo.io.*\")\n";
		require += "\n";
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
		toReturn += "		dojo.io.bind({\n";
		toReturn += "	 		 url: createUrl(e),\n";
		toReturn += "	 		 mimetype: \"text/plain\",\n";
		toReturn += "	  		 load: function(type, data, evt) {}\n";
		toReturn += "		});\n";
		toReturn += "	});\n";
		toReturn += "}\n";
		toReturn += "dojo.event.connect(dojo, \"loaded\", \"initDrop" + container.getMarkupId() + "\");\n";
		toReturn += "</script>\n";
		return toReturn;
	}
	
	@Override
	public void onRequest()
	{
		super.onRequest();
	}
	
	/**
	 * Gets the resource to render to the requester.
	 * 
	 * @return the resource to render to the requester
	 */
	protected final IResourceStream getResponse()
	{
		container.onAjaxModelUpdated();
		return container.getResponseResourceStream();
	}
	


}
