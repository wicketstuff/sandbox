package wicket.contrib.tinymce;

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
    public InPlaceEditComponent(String id, String text) {
        super(id, new Model(text));
        setEscapeModelStrings(false);
        InPlaceSaveBehavior saveBehavior = new InPlaceSaveBehavior();
        add(saveBehavior);
        WicketSavePlugin savePlugin = new WicketSavePlugin(saveBehavior);
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
        add(new InPlaceEditBehavior(settings));
    }

    public String getInputName() {
        return getMarkupId();
    }

    protected final void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        replaceComponentTagBody(markupStream, openTag, getValue());
    }
}
