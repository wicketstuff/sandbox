package org.wicketstuff.dojo.examples.lazylist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.list.lazy.DojoLazyLoadingListContainer;
import org.wicketstuff.dojo.markup.html.list.lazy.DojoLazyLoadingRefreshingView;

public class LazyTableSample extends WicketExamplePage {
	
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
			ArrayList list = new ArrayList();
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
