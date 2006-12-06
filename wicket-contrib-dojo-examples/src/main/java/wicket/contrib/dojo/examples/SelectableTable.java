package wicket.contrib.dojo.examples;

import java.util.ArrayList;
import java.util.List;

import wicket.ajax.AjaxEventBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.ClientEvent;
import wicket.contrib.dojo.html.list.table.DojoSelectableListContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class SelectableTable extends WebPage {

	static final List<String> objList  = new  ArrayList<String>();

	
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
		ListView list = null;
		DojoSelectableListContainer container = new DojoSelectableListContainer(this, "container"){
			@Override
			public void onChoose(AjaxRequestTarget target, Object o) {
				target.appendJavascript("alert('dblClick')");
				
			}
		};
		list = new ListView(container, "list", objList){

			@Override
			protected void populateItem(final ListItem item) {
				new Label(item,"label",(String)item.getModelObject());
				item.add(new AjaxEventBehavior(ClientEvent.DBLCLICK){

					@Override
					protected void onEvent(AjaxRequestTarget target) {
						target.appendJavascript("alert('You dblClick on item number " + item.getIndex() + "')");
						
					}
					
				});
			}
			
		};
	}

}
