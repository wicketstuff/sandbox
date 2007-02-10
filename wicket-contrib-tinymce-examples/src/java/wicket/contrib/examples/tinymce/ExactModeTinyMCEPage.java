package wicket.contrib.examples.tinymce;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu COSTAN
 */
@SuppressWarnings("unchecked")
public class ExactModeTinyMCEPage extends TinyMCEBasePage {
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public ExactModeTinyMCEPage() {

		TextArea textArea1 = new TextArea(this, "ta1", new Model(TEXT));
		TextArea textArea2 = new TextArea(this, "ta2", new Model(TEXT));
		FeedbackPanel panel = new FeedbackPanel(this, "feedback");

		TinyMCESettings settings = new TinyMCESettings(Mode.exact);
		settings.enableTextArea(textArea1);
		TinyMCEPanel tinyMCEPanel = new TinyMCEPanel(this, "tinyMCE", settings);
	}

	private String TEXT = "Some <b>element</b>, this is to be editor 1.";
}
