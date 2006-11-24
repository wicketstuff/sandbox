package wicket.contrib.dojo.examples;

import java.util.ArrayList;
import java.util.List;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.html.list.table.DojoSelectableListContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class SelectableTable extends WebPage {

	static final List objList  = new  ArrayList();
	static final List objList2  = new  ArrayList();

	
	public SelectableTable() {
		super();
		if (objList.size() == 0){
			objList.add("foo1");
			objList.add("foo1");
		}
		if (objList2.size() == 0){
			objList2.add("toto");
			objList2.add("tata");
		}
		
		ListView list = null;
		DojoSelectableListContainer container = new DojoSelectableListContainer("container"){

			public void onChoose(AjaxRequestTarget target, Object Selected) {
				System.out.println("selected");
				target.appendJavascript("alert('dblClick')");
				//setResponsePage(DojoFXTestPage.class);
			}

			public void onSelection(AjaxRequestTarget target, List selected) {
				System.out.println("selected");
			}
			
		};
		container.setAjaxModeOnChoose(false);
		this.add(container);
		list = new ListView("list", objList){

			protected void populateItem(final ListItem item) {
				item.add(new Label("label",(String)item.getModelObject()));
			}
			
		};
		container.add(list);
		
		
		
		ListView list2 = null;
		DojoSelectableListContainer container2 = new DojoSelectableListContainer("container2"){

			public void onChoose(AjaxRequestTarget target, Object Selected) {
				System.out.println("2Choose");
				target.appendJavascript("alert('dblClick')");
				setResponsePage(DojoFXTestPage.class);
			}
			@Override
			public void onNonAjaxChoose(Object selected) {
				System.out.println(selected);
			}
			public void onSelection(AjaxRequestTarget target, List selected) {
				System.out.println(selected);
			}

		};
		container2.setAjaxModeOnChoose(false);
		this.add(container2);
		list2 = new ListView("list2", objList2){

			protected void populateItem(final ListItem item) {
				item.add(new Label("label",(String)item.getModelObject()));
			}
			
		};
		container2.add(list2);
	}

}
