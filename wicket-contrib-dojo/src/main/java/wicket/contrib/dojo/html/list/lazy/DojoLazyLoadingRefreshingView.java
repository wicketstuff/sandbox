package wicket.contrib.dojo.html.list.lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.markup.repeater.RefreshingView;
import wicket.markup.repeater.data.IDataProvider;
import wicket.model.IModel;
import wicket.model.Model;

public abstract class DojoLazyLoadingRefreshingView extends RefreshingView
{
	private int first = 0;
	private int count = 30;
	
	private DojoLazyLoadingListContainer parent;
	IDataProvider dataProvider;

	public DojoLazyLoadingRefreshingView(String id, IDataProvider dataProvider) {
		super(id);
		this.dataProvider = dataProvider;
		this.parent = parent;
	}


	public DojoLazyLoadingRefreshingView(String id) {
		super(id);
		this.parent = parent;
	}

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
		
		Iterator it = dataProvider.iterator(first, count);
		while (it.hasNext())
		{
			toReturn.add(dataProvider.model(it.next()));
		}

		return toReturn.iterator();
	}
}
