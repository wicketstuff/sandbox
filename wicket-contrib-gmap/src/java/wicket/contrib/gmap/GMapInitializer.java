package wicket.contrib.gmap;

import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;

/**
 * @author Iulian-Corneliu Costan
 */
public class GMapInitializer implements IInitializer
{

    /* thanks to <a href="http://mentalized.net/activity-indicators/">these guys</a> for so nice indicators */
    public static final String INDICATOR = "indicator.white.gif";

    public void init(Application application)
    {
        PackageResource.bind(Application.get(), GMapPanel.class, INDICATOR);
    }
}
