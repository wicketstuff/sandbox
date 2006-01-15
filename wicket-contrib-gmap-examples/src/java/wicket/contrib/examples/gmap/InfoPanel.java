package wicket.contrib.examples.gmap;

import wicket.markup.html.panel.Panel;
import wicket.markup.html.list.ListView;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.ExternalLink;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Iulian-Corneliu Costan
 */
public class InfoPanel extends Panel {
    public InfoPanel(final String id) {
        super(id);
        add(new ExternalLink("wicketLink", "http://wicket.sourceforge.net"));
        add(new ListView("developers", getDevelopers()) {
            protected void populateItem(final ListItem item) {
                Developer developer = (Developer) item.getModelObject();
                item.add(new Label("firstname", developer.getFirstname()));
                item.add(new Label("lastname", developer.getLastname()));
                item.add(new Label("email", developer.getEmail()));
            }
        });
    }

    private List<Developer> getDevelopers() {
        List<Developer> developers = new ArrayList<Developer>();
        developers.add(new Developer("Eelco", "Hillenius", "eelco12 at users.sourceforge.net"));
        developers.add(new Developer("Jonathan", "Locke", "jonathanlocke at users.sourceforge.net"));
        developers.add(new Developer("Martijn", "Dashorst", "dashorst at users.sourceforge.net"));
        developers.add(new Developer("Juergen", "Donnerstag", "jdonnerstag at users.sourceforge.net"));
        return developers;
    }

    private class Developer {
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
