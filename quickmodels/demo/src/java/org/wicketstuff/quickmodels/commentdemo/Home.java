package org.wicketstuff.quickmodels.commentdemo;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.persistence.queries.FieldQueryElement;
import org.wicketstuff.persistence.queries.StringContainsValue;
import org.wicketstuff.quickmodels.ModelBuilder;
import org.wicketstuff.quickmodels.PojoCollectionModel;
import org.wicketstuff.quickmodels.Queries;

/**
 * Home page for the database demo application.  Can be instantiated with
 * no PageParameters and will show all comments in the database.  Can be
 * instantiated with a PageParameters containing a value for the key "name"
 * and will show all comments by people whose names are an exact match.
 * Can be instantiated with a PageParameters containing a value for the key
 * "text" and will show all comments that contain that text.
 * 
 * @author Tim Boudreau
 */
public class Home extends WebPage {
    public Home() {
        this (null);
    }
    
    public Home(final PageParameters params) {
        //The real logic is in the three panels we add:
        CommentsPanel comments = new CommentsPanel ("comments") {
            //Override this so that, if we were passed a PageParameters
            //object (by the SearchPanel) then we will do a query that
            //limits what objects are in the model to ones whose name
            //property matches the one searched for.  Otherwise display
            //all Comment instances in the database.
            @Override
            protected PojoCollectionModel<Comment> createModel() {
                ModelBuilder builder = null;
                if (params != null) {
                    String name = params.getString("name");
                    if (name == null) {
                        //User clicked the Name Search button
                        String text = params.getString("text");
                        if (text != null) {
                            //User clicked the full text search button
                            builder = Queries.COMPLEX.builder(Comment.class);
                            
                            //A special value that instructs the engine to
                            //search within a string
                            StringContainsValue checkForString =
                                    new StringContainsValue(text,
                                    true);
                            
                            //Describes an instance of Comment that has a 
                            //field called "comment" of the type String
                            FieldQueryElement lookFor =
                                new FieldQueryElement("comment", String.class,
                                    checkForString, false);
                            
                            builder.setObject (lookFor);
                        }
                    } else {
                        builder = 
                                Queries.QUERY.builder(Comment.class);
                        builder.setFieldName("name");
                        builder.setFieldType(String.class);
                        builder.setFieldValue(name);
                    }
                }
                if (builder == null) {
                    //no page parameters or nothing worthwhile in it
                    builder = Queries.OF_TYPE.builder(Comment.class);
                }
                return builder.multi(getDatabase());
            }
        };
        add (comments);
        AddCommentPanel adder = new AddCommentPanel ("addpanel");
        add (adder);
        NameSearchPanel nameSearch = new NameSearchPanel ("nameSearch");
        add (nameSearch);
        TextSearchPanel textSearch = new TextSearchPanel("textSearch");
        add (textSearch);
    }
}
