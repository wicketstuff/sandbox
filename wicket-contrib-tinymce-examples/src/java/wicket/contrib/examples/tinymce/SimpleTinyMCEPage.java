package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMCEPanel;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SimpleTinyMCEPage extends TinyMCEBasePage
{

    public SimpleTinyMCEPage()
    {
        add(new TinyMCEPanel("tinyMCE"));
        add(new TextArea("ta", new Model(TEXT)));
    }

    private String TEXT = "Some <b>element</b>, this is to be editor 1.";
}
