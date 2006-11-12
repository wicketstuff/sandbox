package wicket.contrib.dojo.examples;

import java.util.ArrayList;
import java.util.List;

import wicket.contrib.dojo.html.list.DojoOrderableListView;
import wicket.contrib.dojo.html.list.DojoOrderableListViewContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;

public class OrderableList extends WebPage {

	static List<String> objList;
	
	public OrderableList() {
		super();
		objList = new  ArrayList<String>();
		objList.add("foo1");
		objList.add("bar1");
		objList.add("foo2");
		objList.add("bar2");
		DojoOrderableListViewContainer container = new DojoOrderableListViewContainer(this, "container");
		DojoOrderableListView list = new DojoOrderableListView(container, "list", objList){

			@Override
			protected void populateItem(ListItem item) {
				new Label(item,"label",(String)item.getModelObject());
				
			}
			
		};
	}

}
