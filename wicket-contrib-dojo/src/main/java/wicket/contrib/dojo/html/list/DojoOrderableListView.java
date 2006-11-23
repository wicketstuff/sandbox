package wicket.contrib.dojo.html.list;

import java.util.List;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.dojodnd.DojoDragContainerHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;
import wicket.model.Model;

public abstract class DojoOrderableListView extends ListView
{
	DojoOrderableListViewContainer container;
	int pos = 0; 
	private String dragId;

	public DojoOrderableListView(DojoOrderableListViewContainer parent, String id, IModel model)
	{
		super(id, model);
		container = parent;
	}

	public DojoOrderableListView(DojoOrderableListViewContainer parent, String id, List list)
	{
		super(id, list);
		container = parent;
	}

	public DojoOrderableListView(DojoOrderableListViewContainer parent, String id)
	{
		super(id);
		container = parent;
	}
	
	public String generateId(){
		return container.getMarkupId()+ "_list_" + (pos++);
		
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
	}

	protected void renderItem(ListItem item)
	{
		String id = generateId();
		item.add(new AttributeAppender("id", true, new Model(id),""));
		dragId = "*";
		super.renderItem(item);
	}


	protected void populateItem(ListItem item)
	{
		// TODO Auto-generated method stub
		
	}

}
