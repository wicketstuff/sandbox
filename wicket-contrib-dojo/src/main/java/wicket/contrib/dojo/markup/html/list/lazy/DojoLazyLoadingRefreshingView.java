package wicket.contrib.dojo.markup.html.list.lazy;

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
		setOutputMarkupId(true);
	}


	public DojoLazyLoadingRefreshingView(String id) {
		super(id);
		this.parent = parent;
		setOutputMarkupId(true);
	}

/*

	public abstract Iterator iterator(int first, int count);
	
	
	public IModel model(Object object){
		if (!(object instanceof Serializable)){
			throw new IllegalArgumentException("The model Object should be Serializable in a DojoLazyLoadingListContainer");
		}
		return new Model((Serializable)object);
	}

	public void detach(){}
*/

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
