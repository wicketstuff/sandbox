package wicket.contrib.gmap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;

/**
 * Base wicket component that match &ltscript&gt html tag.
 *
 * @author Iulian-Corneliu Costan
 */
abstract class JavaScriptComponent extends WebComponent
{

    public JavaScriptComponent(final String id)
    {
        super(id);
    }

    protected void onComponentTag(final ComponentTag tag)
    {
        checkComponentTag(tag, "script");
//        add(new AttributeModifier("type", true, new Model("text/javascript")));
//        add(new AttributeModifier("language", true, new Model("JavaScript")));

    }

    protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
    {
        String js = onJavaScriptComponentTagBody();
        replaceComponentTagBody(markupStream, openTag, js);
    }

    public abstract String onJavaScriptComponentTagBody();
}
