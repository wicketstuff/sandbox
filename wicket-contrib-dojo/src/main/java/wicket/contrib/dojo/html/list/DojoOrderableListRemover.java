package wicket.contrib.dojo.html.list;

import java.util.Iterator;

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxEventBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.markup.html.list.ListItem;
import wicket.model.IModel;

public class DojoOrderableListRemover extends AjaxLink{

	// item to remove
	private ListItem item;
	
	public DojoOrderableListRemover(String id, ListItem item)
	{
		super(id);
		this.item = item; 
	}
	
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		check();
	}

	public final void onClick(AjaxRequestTarget target)
	{
		if (beforeRemoving()){
			DojoOrderableListView parent = ((DojoOrderableListView)this.item.getParent());
			DojoOrderableListViewContainer granParent = (DojoOrderableListViewContainer)parent.getParent();
			parent.getList().remove(parent.getList().indexOf(this.item.getModelObject()));
			parent.modelChanged();
			
			target.addComponent(granParent);
			onRemove(target);
		}
		else {
			onNotRemove(target);
		}
	}
	
	private void check(){
		if (! (item.getParent() instanceof DojoOrderableListView)){
			throw new WicketRuntimeException("Parent of item should be a DojoOrderableListView");
		}
		if (! (item.getParent().getParent() instanceof DojoOrderableListViewContainer)){
			throw new WicketRuntimeException("GranParent of item should be a DojoOrderableListViewContainer");
		}
	}

	protected void onRemove(AjaxRequestTarget target){
		
	}
	
	private void onNotRemove(AjaxRequestTarget target)
	{
		
	}
	
	protected boolean beforeRemoving(){
		return true;
	}
	


	
	
}