package wicket.contrib.gmap;

import wicket.AttributeModifier;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu COSTAN
 */
class GMapReference extends WebMarkupContainer {

    GMapReference(final String id, String model) {
        super(id);
        add(new AttributeModifier("src", true, new Model(model)));
    }
}
