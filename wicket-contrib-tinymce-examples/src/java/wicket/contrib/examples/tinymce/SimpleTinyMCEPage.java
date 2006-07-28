package wicket.contrib.examples.tinymce;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.markup.html.form.TextArea;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SimpleTinyMCEPage extends TinyMCEBasePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public SimpleTinyMCEPage()
    {
        new TinyMCEPanel(this, "tinyMCE");
        new TextArea<String>(this, "ta", new Model<String>(TEXT));
    }

    private String TEXT = "Some <b>element</b>, this is to be editor 1.";
}
