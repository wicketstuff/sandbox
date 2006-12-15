package wicket.contrib.dojo.html.list.lazy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.markup.repeater.RefreshingView;
import wicket.markup.repeater.data.IDataProvider;
import wicket.model.IModel;
import wicket.model.Model;

public abstract class DojoLazyLoadingRefreshingView extends RefreshingView implements IDataProvider
{
	private int first = 0;
	private int count = 30;
	
	private DojoLazyLoadingListContainer parent;
	
	public DojoLazyLoadingRefreshingView(String id, IModel model) {
		super(id, model);
		this.parent = parent;
	}


	public DojoLazyLoadingRefreshingView(String id) {
		super(id);
		this.parent = parent;
	}


	/**
	 * @param first 
	 * @param count 
	 * @return 
	 * 
	 */
	public abstract Iterator iterator(int first, int count);
	
	
	public IModel model(Serializable object){
		return new Model(object);
	}

	public void detach(){}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getFirst()
	{
		return first;
	}

	public void setFirst(int first)
	{
		this.first = first;
	}

	protected final Iterator getItemModels(){
		
		final List toReturn = new ArrayList();
		
		Iterator it = iterator(first, count);
		while (it.hasNext())
		{
			toReturn.add(model(it.next()));
		}

		return toReturn.iterator();
	}
}
