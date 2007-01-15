package wicket.contrib.gmap;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.model.Model;

/**
 * Base wicket component that match &ltscript&gt html tag.
 *
 * @author Iulian-Corneliu Costan
 */
abstract class JavaScriptComponent extends WebComponent
{

    public JavaScriptComponent(MarkupContainer parent, final String id)
    {
        super(parent, id);
    }

    protected void onComponentTag(final ComponentTag tag)
    {
        checkComponentTag(tag, "script");
        add(new AttributeModifier("type", true, new Model("text/javascript")));
        add(new AttributeModifier("language", true, new Model("JavaScript")));

    }

    protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
    {
        String js = onJavaScriptComponentTagBody();
        replaceComponentTagBody(markupStream, openTag, js);
    }

    public abstract String onJavaScriptComponentTagBody();
}
