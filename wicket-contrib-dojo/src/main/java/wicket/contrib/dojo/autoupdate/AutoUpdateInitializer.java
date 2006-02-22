package wicket.contrib.dojo.autoupdate;

import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;

/**
 * Innitializer class for autoupdate handler
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 *
 */
public class AutoUpdateInitializer implements IInitializer
{

	/**
	 * @see wicket.IInitializer#init(wicket.Application)
	 */
	public void init(Application application)
	{
		PackageResource.bind(application, DojoAutoUpdateHandler.class, "autoupdate.js");
		
	}

}
