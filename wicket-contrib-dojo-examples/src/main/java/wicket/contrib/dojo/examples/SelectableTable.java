package wicket.contrib.dojo.examples;

import java.util.ArrayList;
import java.util.List;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.html.list.table.DojoSelectableList;
import wicket.contrib.dojo.html.list.table.DojoSelectableListContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;

public class SelectableTable extends WebPage {

	static final List objList  = new  ArrayList();

	
	public SelectableTable() {
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
		DojoSelectableList list = null;
		DojoSelectableListContainer container = new DojoSelectableListContainer("container"){

			public void onChoose(AjaxRequestTarget target) {
				System.out.println("Choose");
			}

			public void onSelection(AjaxRequestTarget target, List selected) {
				System.out.println("Selection");
			}
			
		};
		this.add(container);
		list = new DojoSelectableList("list", objList, container){

			protected void populateItem(final ListItem item) {
				item.add(new Label("label",(String)item.getModelObject()));
			}
			
		};
		container.add(list);
	}

}
