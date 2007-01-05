package wicket.contrib.dojo.examples;

import java.util.ArrayList;
import java.util.List;

import wicket.contrib.dojo.markup.html.list.DojoOrderableListRemover;
import wicket.contrib.dojo.markup.html.list.DojoOrderableListView;
import wicket.contrib.dojo.markup.html.list.DojoOrderableListViewContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;

public class OrderableList extends WebPage {

	static final List<String> objList  = new  ArrayList<String>();

	
	public OrderableList() {
		super();
		if (objList.size() == 0){
			objList.add("foo1");
			objList.add("bar1");
			objList.add("foo2");
			objList.add("bar2");
			objList.add("foo3");
			objList.add("bar3");
			objList.add("foo4");
			objList.add("bar4");
			objList.add("foo5");
			objList.add("bar5");
			objList.add("foo6");
			objList.add("bar6");
		}
		DojoOrderableListViewContainer container = new DojoOrderableListViewContainer(this, "container");
		DojoOrderableListView list = new DojoOrderableListView(container, "list", objList){

			@Override
			protected void populateItem(ListItem item) {
				new Label(item,"label",(String)item.getModelObject());
				new DojoOrderableListRemover(item, "remover", item);
				
			}
			
		};
	}

}
