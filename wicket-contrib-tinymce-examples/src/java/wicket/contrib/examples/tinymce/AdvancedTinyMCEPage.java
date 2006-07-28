package wicket.contrib.examples.tinymce;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.markup.html.form.TextArea;
import wicket.model.Model;

/**
 * @author Iulian Costan (iulian.costan@gmail.com)
 */
public class AdvancedTinyMCEPage extends TinyMCEBasePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public AdvancedTinyMCEPage()
	{
		TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);

		new TinyMCEPanel(this, "tinyMCE", settings);
		new TextArea<String>(this, "ta", new Model<String>(TEXT));
	}

	private String TEXT = "Some <strong>element</strong>, this is to be editor 1. <br/>\n"
			+ "This editor instance has a 100% width to it. \n"
			+ "<p>Some paragraph. <a href=\"http://www.sourceforge.net/\">Some link</a></p>\n"
			+ "<img src=\"logo.jpg\" border=\"0\" />";
}
