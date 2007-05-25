package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class AdvancedTinyMCEPage extends TinyMCEBasePage
{

    public AdvancedTinyMCEPage()
    {
        TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);

        TextArea textArea = new TextArea("ta", new Model(TEXT));
        textArea.add(new TinyMceBehavior(settings, false));
		add(textArea);
    }

    private String TEXT = "Some <strong>element</strong>, this is to be editor 1. <br/>\n" +
            "This editor instance has a 100% width to it. \n" +
            "<p>Some paragraph. <a href=\"http://www.sourceforge.net/\">Some link</a></p>\n" +
            "<img src=\"logo.jpg\" border=\"0\" />";
}
