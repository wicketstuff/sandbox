package org.apache.wicket;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.BaseEntityDetachableModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.persistence.domain.Message;

/**
 * Homepage
 */
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage() {
		super();
		// Add the simplest type of label
		add(new Label("message",
				"If you see this message wicket is properly configured and running"));
		add(new ListView("messages",
				new LoadableDetachableModel<List<Message>>() {
					@Override
					protected List<Message> load() {
						// TODO Auto-generated method stub
						return messageRepository.getAllAsList();
					}
				}

		) {

			@Override
			protected void populateItem(ListItem item) {
				item.setModel(new CompoundPropertyModel(
						new BaseEntityDetachableModel<Message>((Message) item
								.getModelObject())));
				item.add(new Label("message"));

			}

		});
		// TODO Add your page's components here
	}
}
