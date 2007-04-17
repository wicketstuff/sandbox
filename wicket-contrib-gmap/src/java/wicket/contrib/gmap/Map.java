package wicket.contrib.gmap;

import wicket.AttributeModifier;
import wicket.markup.html.WebComponent;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu Costan
 */
class Map extends WebComponent
{
    public Map(final String id, int width, int height)
    {
        super(id);
        add(new AttributeModifier("style", new Model("width: " + width + "px; height: " + height + "px")));
    }
}
