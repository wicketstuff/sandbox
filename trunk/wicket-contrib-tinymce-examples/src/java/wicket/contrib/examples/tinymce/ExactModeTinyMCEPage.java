package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.SpellCheckPlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class ExactModeTinyMCEPage extends TinyMCEBasePage {
	private static final long serialVersionUID = 1L;

	public ExactModeTinyMCEPage() {

		TinyMCESettings settings = new TinyMCESettings(Mode.exact,
				Theme.advanced);
		settings.setLanguage(TinyMCESettings.Language.EN);

		SpellCheckPlugin spellCheckPlugin = new SpellCheckPlugin();
		settings.add(spellCheckPlugin.getSpellCheckButton(),
				TinyMCESettings.Toolbar.first, TinyMCESettings.Position.after);

		TextArea textArea1 = new TextArea("ta1", new Model(TEXT));
		textArea1.add(new TinyMceBehavior(settings, false));
		TextArea textArea2 = new TextArea("ta2", new Model(TEXT));

		add(textArea1);
		add(textArea2);
	}

	private String TEXT = "Some <b>element</b>, this is to be editor 1.";
}
