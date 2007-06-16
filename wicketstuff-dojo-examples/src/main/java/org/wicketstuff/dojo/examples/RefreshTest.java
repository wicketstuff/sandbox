package org.wicketstuff.dojo.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.split.DojoSplitContainer;
import org.wicketstuff.dojo.markup.html.container.tab.DojoTabContainer;
import org.wicketstuff.dojo.markup.html.list.table.DojoSelectableListContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class RefreshTest extends WebPage {

	static final List objList  = new  ArrayList();
	
	public RefreshTest() {
		
		super();
		
		final DojoTabContainer page= new DojoTabContainer("tab3", "tab inside");
		final DojoSelectableListContainer containerList = new DojoSelectableListContainer("containerList"){
			public void onChoose(AjaxRequestTarget target, Object o) {
				target.appendJavascript("alert('dblClick')");
			}
		};
		
		
		final DojoSplitContainer container = new DojoSplitContainer("splitContainer");
		add(container);
		container.setOrientation(DojoSplitContainer.ORIENTATION_VERTICAL);
		container.setHeight("500px");
		
		DojoSimpleContainer first = new DojoSimpleContainer("tab1", "title1");
		first.add(new AjaxLink("ajaxlink"){
			public void onClick(AjaxRequestTarget target) {
				//THIS A HACK TO AVOID A RESIZE BUG
				target.appendJavascript("dojo.event.connect(dojo.widget.byId('" + container.getMarkupId() + "'), 'onResize', function() {dojo.widget.byId('" + page.getMarkupId() + "').onResized()});");
				target.addComponent(page);
				target.addComponent(containerList);
			}
		});
		
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
		
		first.add(containerList);
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
		containerList.add(list);
		
		
		container.add(first);
		
		container.add(new DojoSimpleContainer("tab2", "title2"));
		
		DojoSimpleContainer containerOuter = new DojoSimpleContainer("tab3outer","tab3");
		page.add(new DojoSimpleContainer("tab31", "tab31"));
		page.add(new DojoSimpleContainer("tab32", "tab32"));
		page.add(new DojoSimpleContainer("tab33", "tab33"));
		page.add(new DojoSimpleContainer("tab34", "tab34"));
		containerOuter.add(page);
		page.setHeight("100%");
		container.add(containerOuter);
		containerOuter.setHeight("100%");
		
	}

}
