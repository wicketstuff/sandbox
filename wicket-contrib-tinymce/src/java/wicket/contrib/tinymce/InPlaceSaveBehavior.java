package wicket.contrib.tinymce;

import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.tinymce.settings.WicketSavePlugin;

/**
 * This behavior adds saving functionality to an editor for in-place
 * editing of content. In most cases you will want to use {@link InPlaceEditComponent}
 * instead of this class directly.
 */
public class InPlaceSaveBehavior extends AbstractDefaultAjaxBehavior {
    private String saveEditorScriptName;

    protected final void respond(AjaxRequestTarget target) {
        Request request = RequestCycle.get().getRequest();
        String newContent = request.getParameter(createParam(getComponent()));
        newContent = onSave(newContent);
        Component component = getComponent();
        component.getModel().setObject(newContent);
        target.addComponent(component);
    }

    /** Returns the name of the JavaScript function that handles
     * the save event. (Replace the editor with the saved content
     * in the original component).
     * @return Name of the javascript function, used by WicketSave
     * plugin, see {@link WicketSavePlugin}
     */
    public final String getSaveCallbackName() {
        if (saveEditorScriptName == null) {
            String uuid = UUID.randomUUID().toString().replace('-', '_');
            saveEditorScriptName = "savemce_" + uuid;
        }
        return saveEditorScriptName;
    }

    /**
     * This method gets called before the new content as received from the
     * TinyMce editor is pushed to the website. Override it to add additional
     * processing to the content.
     * @param newContent The content as received from the editor.
     * @return The content that will be pushed back to your website.
     */
    protected String onSave(String newContent) {
        return newContent;
    }

    private final String createParam(Component component) {
        return "new_" + component.getMarkupId();
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        // Don't pass an id, since every EditableComponent will have its own
        // submit script:
        response.renderJavascript(createSaveScript(), null);
    }

    private final String createSaveScript() {
        String callback = getWicketPostScript();
        String markupId = getComponent().getMarkupId();
        return "function " + getSaveCallbackName() + "(inst) {\n" //
                + " var content = inst.getBody().innerHTML;\n" //
                + " tinyMCE.execCommand('mceRemoveControl', true, '" + markupId + "');\n" //
                + " " + callback + "\n" //
                + "}";
    }

    private final String getWicketPostScript() {
        return generateCallbackScript(
                "wicketAjaxPost('" + getCallbackUrl(false) + "', Wicket.Form.encode('" + createParam(getComponent())
                        + "') + '=' + Wicket.Form.encode(content) + '&'").toString();
    }
}
