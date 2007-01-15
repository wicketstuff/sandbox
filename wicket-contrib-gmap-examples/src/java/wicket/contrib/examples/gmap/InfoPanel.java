package wicket.contrib.examples.gmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.ExternalLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;

/**
 * @author Iulian-Corneliu Costan
 */
public class InfoPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public InfoPanel(MarkupContainer parent, final String id) {
		super(parent, id);
		new ExternalLink(this, "wicketLink", "http://wicket.sourceforge.net");
		new ListView<Developer>(this, "developers", getDevelopers()) {
			private static final long serialVersionUID = 1L;

			protected void populateItem(final ListItem<Developer> item) {
				Developer developer = item.getModelObject();
				new Label(this, "firstname", developer.getFirstname());
				new Label(this, "lastname", developer.getLastname());
				new Label(this, "email", developer.getEmail());
			}
		};
	}

	private List<Developer> getDevelopers() {
		List<Developer> developers = new ArrayList<Developer>();
		developers.add(new Developer("Eelco", "Hillenius",
				"eelco12 at users.sourceforge.net"));
		developers.add(new Developer("Jonathan", "Locke",
				"jonathanlocke at users.sourceforge.net"));
		developers.add(new Developer("Martijn", "Dashorst",
				"dashorst at users.sourceforge.net"));
		developers.add(new Developer("Juergen", "Donnerstag",
				"jdonnerstag at users.sourceforge.net"));
		return developers;
	}

	private class Developer implements Serializable {
		private static final long serialVersionUID = 1L;

		private String firstname;

		private String lastname;

		private String email;

		public Developer(String firstname, String lastname, String email) {
			this.firstname = firstname;
			this.lastname = lastname;
			this.email = email;
		}

		public String getFirstname() {
			return firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public String getEmail() {
			return email;
		}
	}
}
