package wicket.contrib.tinymce;

import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * This behavior adds in-place editing functionality to wicket components. In most cases you will want to use
 * {@link InPlaceEditComponent} instead of this class directly.
 */
public class InPlaceEditBehavior extends AbstractBehavior {
    private TinyMCESettings settings;
    // TODO: minified code; todo push to super class, todo remove reference from panel,
    // put into bhavior.
    private static final ResourceReference REFERENCE = new ResourceReference(TinyMCEPanel.class,
            "tiny_mce/tiny_mce_src.js");
    private String startEditorScriptName;
    private Component component;

    /**
     * Construct in-place-editing behavior to a component. It makes the content of the component editable with a TinyMce
     * WYSIWYG editor.
     * 
     * @param settings
     *            TinyMceSettings for the editor when opened.
     * @param triggerComponent
     *            Component that will get an onclick event to make the component that this behavior is added to
     *            editable. Can be the editable component itself, but can also be another component, e.g. a button. If
     *            set to null, you will have to toggle the editable state via a call to the javascriptfunction with
     *            name: {@link #getStartEditorScriptName()}
     */
    public InPlaceEditBehavior(TinyMCESettings settings, Component triggerComponent) {
        this.settings = settings;
        if (triggerComponent != null)
            triggerComponent.add(createTriggerBehavior());
    }

    private IBehavior createTriggerBehavior() {
        return new AbstractBehavior() {
            public void onComponentTag(Component component, ComponentTag tag) {
                super.onComponentTag(component, tag);
                tag.put("onclick", getStartEditorScriptName() + "();");
            }
        };
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        if (component == null)
            throw new IllegalStateException("DynamicTinyMceBehavior is not bound to a component");

        // TinyMce javascript:
        response.renderJavascriptReference(REFERENCE);

        response.renderJavascript("" //
                + "function " + getStartEditorScriptName() + "() {" //
                + " tinyMCE.init({" + settings.toJavaScript(false) + " });\n" //
                // TODO: each time or only once?????
                + settings.getLoadPluginJavaScript() //
                + settings.getAdditionalPluginJavaScript() //
                + " tinyMCE.execCommand('mceAddControl',true,'" + component.getMarkupId(true) + "');" //
                + "}", null);
    }

    public void bind(Component component) {
        if (this.component != null)
            throw new IllegalStateException("DynamicTinyMceBehavior can not bind to more than one component");
        super.bind(component);
        component.setOutputMarkupId(true);
        this.component = component;
    }

    /**
     * @return The name of the (no-argument) JavaScript that will replace the component that is bound to this behavior
     *         with a TinyMce editor.
     */
    public final String getStartEditorScriptName() {
        if (startEditorScriptName == null) {
            String uuid = UUID.randomUUID().toString().replace('-', '_');
            startEditorScriptName = "startmce_" + uuid;
        }
        return startEditorScriptName;
    }
}
