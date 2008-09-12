package wicket.contrib.tinymce;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.WicketSavePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings.Align;
import wicket.contrib.tinymce.settings.TinyMCESettings.Location;
import wicket.contrib.tinymce.settings.TinyMCESettings.Position;
import wicket.contrib.tinymce.settings.TinyMCESettings.Toolbar;

public class InPlaceEditComponent extends AbstractTextComponent {
    private InPlaceSaveBehavior inPlaceSaveBehavior;
    private InPlaceEditBehavior inPlaceEditBehavior;

    public InPlaceEditComponent(String id, String text) {
        super(id, new Model(text));
        init(this);
    }

    public InPlaceEditComponent(String id, String text, Component triggerComponent) {
        super(id, new Model(text));
        init(triggerComponent);
    }

    private void init(Component triggerComponent) {
        setEscapeModelStrings(false);
        inPlaceSaveBehavior = new InPlaceSaveBehavior();
        add(inPlaceSaveBehavior);
        WicketSavePlugin savePlugin = new WicketSavePlugin(inPlaceSaveBehavior);
        TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Mode.none, TinyMCESettings.Theme.advanced);
        // TODO: get language from locale....
        // TODO: set settings externally...
        settings.setLanguage(TinyMCESettings.Language.NL);
        settings.setToolbarLocation(Location.top);
        settings.setToolbarAlign(Align.left);
        settings.disableButton(TinyMCESettings.styleselect);
        settings.disableButton(TinyMCESettings.anchor);
        settings.disableButton(TinyMCESettings.image);
        settings.disableButton(TinyMCESettings.cleanup);
        settings.disableButton(TinyMCESettings.help);
        settings.disableButton(TinyMCESettings.code); //
        settings.disableButton(TinyMCESettings.hr);
        settings.disableButton(TinyMCESettings.removeformat);
        settings.disableButton(TinyMCESettings.visualaid);
        settings.disableButton(TinyMCESettings.help);
        settings.add(savePlugin.getSaveButton(), Toolbar.first, Position.before);
        settings.add(savePlugin.getCancelButton(), Toolbar.first, Position.before);
        inPlaceEditBehavior = new InPlaceEditBehavior(settings, triggerComponent);
        add(inPlaceEditBehavior);
    }
    
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        //the name tag is added by AbstractTextComponent, because it expects this
        // element to be an <input> tag. We don't need it, and it will render invalid
        // html if this is not an input tag:
        tag.remove("name");
    }

    public String getInputName() {
        return getMarkupId();
    }

    protected final void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        replaceComponentTagBody(markupStream, openTag, getValue());
    }

    /**
     * @return The name of the (no-argument) JavaScript that will replace this component with a TinyMce editor.
     */
    public final String getStartEditorScriptName() {
        return inPlaceEditBehavior.getStartEditorScriptName();
    }

    public InPlaceSaveBehavior getInPlaceSaveBehavior() {
        return inPlaceSaveBehavior;
    }

    public InPlaceEditBehavior getInPlaceEditBehavior() {
        return inPlaceEditBehavior;
    }
}
