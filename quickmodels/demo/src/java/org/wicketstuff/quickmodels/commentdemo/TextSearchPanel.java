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
final class TextSearchPanel extends DbPanel<Comment> {
    TextSearchPanel(String id) {
        super (id);
        SearchForm form = new SearchForm ("search");
        add (form);
    }
    
    private final class SearchForm extends Form {
        private final TextField textField = new TextField ("text",
                new PropertyModel(this, "text"));
        private String text = null;
        SearchForm (String id) {
            super (id);
            add (textField);
        }

        @Override
        protected void onSubmit() {
            if (textField.getModelObject() == null) {
                //show everything
                setResponsePage(Home.class);
                return;
            }
            PageParameters pp = new PageParameters();
            pp.add("text", textField.getModelObjectAsString());
            //Send our PageParameters to the class, so we filter the
            //list
            setResponsePage(Home.class, pp);
        }
        
        public String getText() {
            return text;
        }
        
        public void setText (String text) {
            this.text = text;
        }
    }
}
