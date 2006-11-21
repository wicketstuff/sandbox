package wicket.contrib.dojo.html.list.table;


import java.util.List;

import wicket.WicketRuntimeException;
import wicket.markup.ComponentTag;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;

public abstract class DojoSelectableList extends  ListView
{

	public DojoSelectableList(DojoSelectableListContainer parent, String id, IModel model)
	{
		super(parent, id, model);
		//we add here behavior to the parent
		getParentContainer().add(new DojoSelectableListContainerHandler(this));
	}

	public DojoSelectableList(DojoSelectableListContainer parent, String id, List list)
	{
		super(parent, id, list);
		//we add here behavior to the parent
		getParentContainer().add(new DojoSelectableListContainerHandler(this));
	}

	public DojoSelectableList(DojoSelectableListContainer parent, String id)
	{
		super(parent, id);
		//we add here behavior to the parent
		getParentContainer().add(new DojoSelectableListContainerHandler(this));
	}
	
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		if (!"tr".equals(getMarkupFragment().getTag().getName())){
			throw new WicketRuntimeException("Tag name for a DojoSelectableList should be 'tr'");
		}
	}
	
	/**
	 * Will be used to add Behavior to the parent (see onBeforeRender)
	 * @return the parent container
	 */
	private DojoSelectableListContainer getParentContainer(){
		return (DojoSelectableListContainer)getParent();
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
	}



}
