package wicket.contrib.tinymce;

import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.markup.html.panel.Panel;
import wicket.markup.html.resources.JavaScriptReference;
import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEPanel extends Panel {

    private TinyMCESettings settings;

    public TinyMCEPanel(final String id) {
        this(id, new TinyMCESettings());
    }

    public TinyMCEPanel(final String id, final TinyMCESettings settings) {
        super(id);

        if (TinyMCESettings.Theme.simple.equals(settings.getTheme())) {
            add(new JavaScriptReference("tinymce", TinyMCEPanel.class, "tiny_mce/tiny_mce.js"));
        } else {
            add(new JavaScriptReference("tinymce", TinyMCEPanel.class, "tiny_mce/tiny_mce_src.js"));
        }
        add(new WebComponent("initScript") {

            protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                String body = "\ntinyMCE.init({" + settings.toJavaScript() + "\n});\n";
                replaceComponentTagBody(markupStream, openTag, body);
            }
        });
    }
}
