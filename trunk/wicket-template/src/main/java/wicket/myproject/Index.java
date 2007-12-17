package wicket.myproject;

import wicket.PageParameters;
import wicket.markup.html.basic.Label;
import wicket.model.StringResourceModel;

/**
 * Basic bookmarkable index page.
 * <p/>
 * NOTE: You can get session properties from MyProjectSession via getMyProjectSession()
 */
public class Index extends MyProjectPage {
    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public Index(final PageParameters parameters) {

        // Add the simplest type of label
        add(new Label("message", "Hello World!"));

        // Add a label with a model that gets its display text from a resource bundle
        // (which is in this case Index.properties).
        // We use key 'label.current.locale' and provide a the current locale
        // for parameter substitution.
        StringResourceModel stringResourceModel = new StringResourceModel(
                "label.current.locale", this, null, new Object[]{getLocale()}
        );
        add(new Label("anotherMessage", stringResourceModel));

        // TODO Add your page's components here
    }
}
