package wicket.contrib.dojo.examples;


import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.AbstractDojoTimerBehavior;
import wicket.contrib.dojo.update.DojoUpdateHandler;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.TextField;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

public class SimpleUpdateExample extends WebPage {

	Label label;
	TextField textField;
	ListView list;
	
	String labelString;
	String textFieldString;
	List listList;
	WebMarkupContainer listContainer;
	
	public SimpleUpdateExample() {
		
		listList = new ArrayList();
		listList.add("a string in listView");
		textFieldString = "changeMe";
		
		
		labelString = "Hello ! Try to click me";
		label = new Label(this, "label", new Model<String>(labelString));
		label.add(new DojoUpdateHandler("onclick"){

			@Override
			public void updateComponents(AjaxRequestTarget target, Component submitter, String value) {
				listList.add("You click on Hello");	
				target.addComponent(listContainer);
			}
			
		});
		
		
		textField = new TextField(this,"textField", new Model<String>(textFieldString));
		textField.add(new DojoUpdateHandler("onblur"){

			@Override
			public void updateComponents(AjaxRequestTarget target, Component submitter, String value) {
				String toAdd = value;
				if (!toAdd.equals("")){
					listList.add(toAdd);
					target.addComponent(listContainer);
				}
			}
			
		});
		
		
		
		listContainer = new WebMarkupContainer(this, "content");
		
		list = new ListView(listContainer, "list", listList){

			@Override
			protected void populateItem(final ListItem item) {
				final String comment = (String)item.getModelObject();
				new Label(item, "result", comment);
			}
			
		};
		listContainer.setOutputMarkupId(true);
	}
	
	
	
}
