package wicket.contrib.dojo.examples;


import java.util.ArrayList;
import java.util.List;

import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.ClientEvent;
import wicket.contrib.dojo.DojoEventBehavior;
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
		label.add(new DojoEventBehavior(ClientEvent.CLICK){
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				listList.add("You click on Hello");	
				target.addComponent(listContainer);
			}
			
		});
		
		
		textField = new TextField(this,"textField", new Model<String>(textFieldString));
		textField.add(new DojoEventBehavior(ClientEvent.BLUR){
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				listList.add("You change field value");	
				target.addComponent(listContainer);
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
