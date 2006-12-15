package wicket.contrib.dojo.examples;

import java.util.ArrayList;
import java.util.Iterator;

import wicket.PageParameters;
import wicket.contrib.dojo.html.list.lazy.DojoLazyLoadingListContainer;
import wicket.contrib.dojo.html.list.lazy.DojoLazyLoadingRefreshingView;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.repeater.Item;

public class LazyTableSample extends WebPage {
	
	public LazyTableSample(PageParameters parameters){
		DojoLazyLoadingListContainer container = new DojoLazyLoadingListContainer(this, "container", 1000);
		DojoLazyLoadingRefreshingView list = new DojoLazyLoadingRefreshingView(container, "table"){

			@Override
			public Iterator iterator(int first, int count) {
				ArrayList<String> list = new ArrayList<String>();
				int i = 0;
				while(i < count){
					list.add("foo" + (first + i++));
				}
				
				//fake a busy and slow machine
				//int j = 0;
				//while (j < 1000000000){j++;}
				
				return list.iterator();
			}

			@Override
			protected void populateItem(Item item) {
				new Label(item, "label",item.getModel());			
			}
			
		};
	}
}
