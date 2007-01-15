package wicket.contrib.gmap;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.markup.html.WebComponent;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu Costan
 */
class Map extends WebComponent
{
    public Map(MarkupContainer parent, final String id, int width, int height)
    {
        super(parent, id);
        add(new AttributeModifier("style", new Model("width: " + width + "px; height: " + height + "px")));
    }
}
