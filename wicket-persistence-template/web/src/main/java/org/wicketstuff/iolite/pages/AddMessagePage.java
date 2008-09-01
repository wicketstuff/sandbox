	package org.wicketstuff.iolite.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.iolite.persistence.domain.Message;

/**
 * Homepage
 */
public class AddMessagePage extends BasePage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	private CompoundPropertyModel<Message> messageModel = new CompoundPropertyModel<Message>(
			new Message());

	public AddMessagePage() {
		super();
		Form<Message> form = new Form<Message>("form") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
				// Save current msg to db
				generalRepository.add(messageModel.getObject());
				// Clear the old
				messageModel.setObject(new Message());
			}
		};
		form.setModel(messageModel);
		form.add(new TextField<String>("message"));
		add(form);
		add(new Label("messageCount",new AbstractReadOnlyModel<String>(){@Override
		public String getObject() {
			
			return " Total number of messages in database "+ generalRepository.getAllAsList(Message.class).size();
		}}));
		// TODO Add your page's components here
	}
}
