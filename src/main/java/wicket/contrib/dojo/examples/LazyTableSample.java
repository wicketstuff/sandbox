package wicket.contrib.dojo.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.list.lazy.DojoLazyLoadingListContainer;
import wicket.contrib.dojo.markup.html.list.lazy.DojoLazyLoadingRefreshingView;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.repeater.Item;
import wicket.markup.repeater.data.IDataProvider;
import wicket.model.IModel;
import wicket.model.Model;

public class LazyTableSample extends WebPage {
	
	final DojoLazyLoadingRefreshingView list;
	
	public LazyTableSample(PageParameters parameters){
		DojoLazyLoadingListContainer container = new DojoLazyLoadingListContainer("container", 1000);
		add(container);
		 list = new DojoLazyLoadingRefreshingView("table", new SampleDataProvider()){
			protected void populateItem(Item item) {
				item.add(new Label("label",item.getModel()));			
			}
			
		};
		container.add(list);
	}
	
	public class SampleDataProvider implements IDataProvider{

		public Iterator iterator(int first, int count) {
			ArrayList<String> list = new ArrayList<String>();
			int i = 0;
			while(i < count){
				list.add("foo" + (first + i++));
			}
			
			return list.iterator();
		}

		public IModel model(Object object) {
			if (!(object instanceof Serializable)){
				throw new IllegalArgumentException("The model Object should be Serializable in a DojoLazyLoadingListContainer");
			}
			return new Model((Serializable)object);
		}

		public int size() {
			return list.getCount();
		}

		public void detach() {}
		
	}
}
