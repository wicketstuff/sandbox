	package org.apache.wicket;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.persistence.domain.Message;

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
				messageRepository.add(messageModel.getObject());
				// Clear the old
				messageModel.setObject(new Message());
			}
		};
		form.setModel(messageModel);
		form.add(new TextField<String>("message"));
		add(form);
		// TODO Add your page's components here
	}
}
