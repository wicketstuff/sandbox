package org.wicketstuff.dojo.examples.selectabletable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.list.table.DojoSelectableListContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

public class SelectableTable extends WicketExamplePage {

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
		ListView list = null;
		DojoSelectableListContainer container = new DojoSelectableListContainer("container"){

			public void onChoose(AjaxRequestTarget target, Object o) {
				target.appendJavascript("alert('dblClick')");
				
			}
		};
		add(container);
		list = new ListView("list", objList){

			protected void populateItem(final ListItem item) {
				item.add(new Label("label",(String)item.getModelObject()));
				item.add(new AjaxEventBehavior("dblClick"){

					protected void onEvent(AjaxRequestTarget target) {
						target.appendJavascript("alert('You dblClick on item number " + item.getIndex() + "')");
						
					}
					
				});
			}
			
		};
		container.add(list);
		
		
		/*------*/
		
		final RepeatingView repeater = new RefreshingView("repeater"){

			protected Iterator getItemModels() {
				ArrayList list = new ArrayList();
				Iterator ite = objList.iterator();
				while (ite.hasNext()){
					list.add(new Model((String)ite.next()));
				}
				return list.iterator();
			}

			protected void populateItem(Item item) {
				item.add(new Label( "label2", item.getModel()));
			}
			
		};
		final DojoSelectableListContainer container2 = new DojoSelectableListContainer("container2"){

			public void onChoose(AjaxRequestTarget target, Object o) {
				System.out.println(o);
				
			}
			public void onSelection(AjaxRequestTarget target, List selected){
				System.out.println(selected);
			}
		};
		add(container2);
		container2.add(repeater);
		container2.setPermanentSelection(true);
		
		add(new DojoLink("refresh"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.addComponent(container2);
			}
			
		});
	}

}
