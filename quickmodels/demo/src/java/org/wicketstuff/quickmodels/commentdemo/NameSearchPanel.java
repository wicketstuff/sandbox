package org.wicketstuff.quickmodels.commentdemo;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.quickmodels.DbPanel;

/**
 * Form which passes a name inside a PageParameters object, to restrict
 * what comments the CommentsPanel shows.
 *
 * @author Tim Boudreau
 */
final class NameSearchPanel extends DbPanel<Comment> {
    NameSearchPanel(String id) {
        super (id);
        SearchForm form = new SearchForm ("search");
        add (form);
    }
    
    private final class SearchForm extends Form {
        private final TextField nameField = new TextField ("name",
                new PropertyModel(this, "name"));
        private String name = null;
        SearchForm (String id) {
            super (id);
            add (nameField);
        }

        @Override
        protected void onSubmit() {
            if (nameField.getModelObject() == null) {
                //show everything
                setResponsePage(Home.class);
                return;
            }
            PageParameters pp = new PageParameters();
            pp.add("name", nameField.getModelObjectAsString());
            //Send our PageParameters to the class, so we filter the
            //list
            setResponsePage(Home.class, pp);
        }
        
        public String getName() {
            return name;
        }
        
        public void setName (String name) {
            this.name = name;
        }
    }
}
