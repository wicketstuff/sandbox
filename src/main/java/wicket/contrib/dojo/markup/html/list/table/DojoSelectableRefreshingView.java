package wicket.contrib.dojo.markup.html.list.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.markup.MarkupStream;
import wicket.markup.repeater.Item;
import wicket.markup.repeater.RefreshingView;
import wicket.markup.repeater.data.IDataProvider;

/**
 * <p>
 * Selectable refreshing view is a refreshing used as
 * {@link DojoSelectableListContainer} direct child to implement a
 * {@link RefreshingView} in a {@link DojoSelectableListContainer}.
 * </p>
 * <p>
 * This componant allow user to pass a {@link IDataProvider} to the
 * {@link DojoSelectableRefreshingView} in order to populate the
 * {@link DojoSelectableListContainer}
 * </p>
 * <p>
 * Moreover this component has an internal cache used to avoid to call iterator
 * each time an Element is queries by Index. See getByIndexMethod
 * </p>
 * <p>
 * <b>Be carefull : cache is never automaticaly invalidate. User has to call
 * invalidateCache to That</b>
 * </p>
 * 
 * @author Vincent Demay
 * 
 */
public abstract class DojoSelectableRefreshingView extends RefreshingView
{
	protected void onRender(MarkupStream markupStream)
	{
		invalidateCache();
		super.onRender(markupStream);
	}

	private DojoSelectableListContainer parent;
	IDataProvider dataProvider;

	List cacheList;

	public DojoSelectableRefreshingView(String id, IDataProvider dataProvider)
	{
		super(id);
		this.dataProvider = dataProvider;
		this.parent = parent;
		setOutputMarkupId(true);
	}

	protected final Iterator getItemModels()
	{
		return getListFromCache().iterator();
	}

	public List getListFromCache()
	{
		if (cacheList == null)
		{
			cacheList = new ArrayList();
			Iterator it = dataProvider.iterator(0, dataProvider.size());
			while (it.hasNext())
			{
				cacheList.add(dataProvider.model(it.next()));
			}
		}
		return cacheList;
	}

	public void invalidateCache()
	{
		cacheList = null;
	}

	public Object getByIndex(int index)
	{
		return getListFromCache().get(index);
	}
}
