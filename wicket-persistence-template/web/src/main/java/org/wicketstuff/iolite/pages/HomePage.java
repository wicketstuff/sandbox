package org.wicketstuff.iolite.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.iolite.persistence.domain.Message;

/**
 * Homepage
 */
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	private LoadableDetachableModel<List<Message>> messageListModel = new LoadableDetachableModel<List<Message>>() {
		@Override
		protected List<Message> load() {
			// TODO Auto-generated method stub
			return generalRepository.getAllAsList(Message.class);
		}
	};

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
		add(new PropertyListView<Message>("messages", messageListModel

		) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Message> item) {
				item.add(new Label("message"));

			}

		});
		// TODO Add your page's components here
	}


}
