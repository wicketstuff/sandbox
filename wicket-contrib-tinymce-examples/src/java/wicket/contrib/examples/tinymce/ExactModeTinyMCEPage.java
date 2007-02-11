package wicket.contrib.examples.tinymce;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.contrib.tinymce.settings.SpellCheckPlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;
import wicket.markup.html.form.TextArea;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class ExactModeTinyMCEPage extends TinyMCEBasePage {
	private static final long serialVersionUID = 1L;

	public ExactModeTinyMCEPage() {

		TextArea textArea1 = new TextArea("ta1", new Model(TEXT));
		TextArea textArea2 = new TextArea("ta2", new Model(TEXT));

		TinyMCESettings settings = new TinyMCESettings(Mode.exact, Theme.advanced);
		settings.setLanguage(TinyMCESettings.Language.RO);
		
        SpellCheckPlugin spellCheckPlugin = new SpellCheckPlugin();
        settings.add(spellCheckPlugin.getSpellCheckButton(), TinyMCESettings.Toolbar.first, TinyMCESettings.Position.after);
		settings.enableTextArea(textArea1);
		
		TinyMCEPanel tinyMCEPanel = new TinyMCEPanel("tinyMCE", settings);

		add(textArea1);
		add(textArea2);
		add(tinyMCEPanel);
	}

	private String TEXT = "Some <b>element</b>, this is to be editor 1.";
}
