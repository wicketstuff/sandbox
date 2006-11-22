package wicket.contrib.dojo.html.list;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;

public class DojoOrderableListViewHandler extends AbstractRequireDojoBehavior
{

	public void setRequire(RequireDojoLibs libs)
	{

	}

	protected void respond(AjaxRequestTarget target)
	{

	}

	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderString(generateDragDefinition(getComponent().getMarkupId()));

	}
	
	private String generateDragDefinition(String id){
		String toReturn = "";
		toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		toReturn += "function initDrag" + id + "(){\n";
		toReturn += "	var children = document.getElementById('" + id + "').getElementsByTagName('div');\n";
		toReturn += "	for(var i=0;  children.length > i ; i++){\n";
		toReturn += "		var drag = new dojo.dnd.HtmlDragSource(children[i], children[i].id);\n";
		toReturn += "	}\n";
		toReturn += "}\n";
		toReturn += "dojo.event.connect(dojo, \"loaded\", \"initDrag" + id + "\");\n";
		toReturn += "</script>\n";
		return toReturn;
	}

}
