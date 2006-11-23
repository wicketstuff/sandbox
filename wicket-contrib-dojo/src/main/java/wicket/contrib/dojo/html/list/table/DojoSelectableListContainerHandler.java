package wicket.contrib.dojo.html.list.table;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;

/**
 * @author Vincent Demay
 *
 */
public class DojoSelectableListContainerHandler extends AbstractRequireDojoBehavior
{
	
	//child of this container
	private DojoSelectableList listView;

	public DojoSelectableListContainerHandler(DojoSelectableList selectableList)
	{
		super();
		listView = selectableList;
	}

	public void setRequire(RequireDojoLibs libs)
	{
		//DO Nothing, the Widget is in the package
	}

	protected final void respond(AjaxRequestTarget target)
	{
		ArrayList selected = new ArrayList();
		String indexList = getComponent().getRequest().getParameter("indexList");
		if (indexList == null){
			if (selected != null){
				((DojoSelectableListContainer)getComponent()).onChoose(target, selected.get(0));
			}
			else
			{
				((DojoSelectableListContainer)getComponent()).onChoose(target, null);
			}
		}else{
			StringTokenizer tokenizer = new StringTokenizer(indexList, ",");
			List all = listView.getList();
			
			while (tokenizer.hasMoreTokens()){
				int pos = Integer.parseInt(tokenizer.nextToken());
				selected.add(all.get(pos));
			}
			
			((DojoSelectableListContainer)getComponent()).setSelected(selected);
			
			((DojoSelectableListContainer)getComponent()).onSelection(target, selected);
		}
	}

	/**
	 * TODO find an other way to Render an as big javascript
	 * TODO put it in js file
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderCSSReference(new ResourceReference(DojoSelectableListContainer.class, "DojoSelectableListContainer.css"));
		response.renderJavascriptReference(new ResourceReference(DojoSelectableListContainer.class, "SelectableTable.js"));
		
		String toReturn="";
		toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		toReturn += "function getSelection(){\n";
		toReturn += "	var container = document.getElementById('" + getComponent().getMarkupId() + "');\n";
		toReturn += "	var body = container.getElementsByTagName('tbody')[0];\n";
		toReturn += "	var rows=body.getElementsByTagName('tr')\n";
		toReturn += "	var selection = '';\n";
		toReturn += "	var index = 0;\n";
		toReturn += "	for(var i=0; i<rows.length; i++){\n";
		toReturn += "		if(rows[i].parentNode==body){\n";
		toReturn += "			if(dojo.html.getAttribute(rows[i],'selected')=='true'){\n";
		toReturn += "				selection += index + ',';\n";
		toReturn += "			}\n";
		toReturn += "			index++;\n";
		toReturn += "		}\n";
		toReturn += "	}\n";
		toReturn += "	return selection;\n";
		toReturn += "};\n";
		toReturn += "</script>\n";
		
		response.renderString(toReturn);
	}
	
	/**
	 * @return javascript that will generate an ajax GET request to this
	 *         behavior *
	 * @param recordPageVersion
	 *            if true the url will be encoded to execute on the current page
	 *            version, otherwise url will be encoded to execute on the
	 *            latest page version
	 */
	protected final CharSequence getCallbackScript()
	{
		return getCallbackScript("wicketAjaxGet('" + super.getCallbackUrl(false, true) + "&indexList=' + getSelection()", null,
				null);
	}
	
	/**
	 * return javascript that will be used to respond to Double click
	 * @return javascript that will be used to respond to Double click
	 */
	protected final CharSequence getDoubleClickCallbackScripts(){
		return getCallbackScript("wicketAjaxGet('" + super.getCallbackUrl(false, true) + "'", null,
				null);
	}

	/**
	 * Add onSelect and on choose event listener
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("onSelect", getCallbackScript());
		tag.put("onChoose", getDoubleClickCallbackScripts());
	}

	

}
