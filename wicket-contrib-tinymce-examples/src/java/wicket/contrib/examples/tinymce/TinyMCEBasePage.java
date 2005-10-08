package wicket.contrib.examples.tinymce;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.markup.html.link.Link;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEBasePage extends WicketExamplePage
{

    public TinyMCEBasePage()
    {
        add(new Link("advanced")
        {

            public void onClick()
            {
                setResponsePage(AdvancedTinyMCEPage.class);
                setRedirect(true);
            }
        });
        add(new Link("simple")
        {

            public void onClick()
            {
                setResponsePage(SimpleTinyMCEPage.class);
                setRedirect(true);
            }
        });
        add(new Link("word")
        {

            public void onClick()
            {
                setResponsePage(WordTinyMCEPage.class);
                setRedirect(true);
            }
        });
    }
}
