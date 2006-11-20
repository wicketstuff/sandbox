package wicket.contrib.examples.tinymce;

import wicket.protocol.http.WebApplication;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEExampleApplication extends WebApplication
{

    public TinyMCEExampleApplication()
    {
    }

    /**
     * @see wicket.Application#getHomePage()
     */
    public Class getHomePage()
    {
        return TinyMCEBasePage.class;
    }
}
