package wicket.contrib.examples.tinymce;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.Control;
import wicket.markup.html.form.TextArea;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class WordTinyMCEPage extends TinyMCEBasePage {

    public WordTinyMCEPage() {
        TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);

        Control control = new Control(Control.DefaultButton.numlist, Control.Toolbar.first, Control.Position.before);
        Control control1 = new Control(Control.DefaultButton.bullist, Control.Toolbar.first, Control.Position.before);
        Control control2 = new Control(Control.DefaultButton.bullist, Control.Toolbar.first, Control.Position.after);
        Control control3 = new Control(Control.DefaultButton.bullist, Control.Toolbar.second, Control.Position.before);
        Control control4 = new Control(Control.DefaultButton.bullist, Control.Toolbar.second, Control.Position.after);
        Control control5 = new Control(Control.DefaultButton.bullist, Control.Toolbar.third, Control.Position.before);
        Control control6 = new Control(Control.DefaultButton.bullist, Control.Toolbar.third, Control.Position.after);

        settings.addControl(control);
        settings.addControl(control1);
        settings.addControl(control2);
        settings.addControl(control3);
        settings.addControl(control4);
        settings.addControl(control5);
        settings.addControl(control6);

        settings.setToolbarAlign(TinyMCESettings.Align.left);
        settings.setToolbarLocation(TinyMCESettings.Location.top);
        settings.setStatusbarLocation(TinyMCESettings.Location.bottom);
        settings.setVerticalResizing(true);

        add(new TinyMCEPanel("tinyMCE", settings));
        add(new TextArea("ta", new Model(TEXT)));
    }

    private static final String TEXT = "<p>Some paragraph</p>" +
            "<p>Some other paragraph</p>" +
            "<p>Some <strong>element</strong>, this is to be editor 1. <br />" +
            "This editor instance has a 100% width to it. </p>" +
            "<p>Some paragraph. <a href=\"http://www.sourceforge.net/\">Some link</a></p>" +
            "<img src=\"logo.jpg\" border=\"0\" /><p>&nbsp;</p>";
}
