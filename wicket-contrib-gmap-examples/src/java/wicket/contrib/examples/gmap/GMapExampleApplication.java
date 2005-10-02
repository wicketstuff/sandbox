package wicket.contrib.examples.gmap;

import wicket.protocol.http.WebApplication;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GMapExampleApplication extends WebApplication
{

    public GMapExampleApplication()
    {
        getPages().setHomePage(HomePage.class);
    }
}
