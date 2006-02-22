package wicket.contrib.markup.html.tooltip;

import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;

/**
 * @author jcompagner
 *
 */
public class TooltipComponentInitializer implements IInitializer
{

	/**
	 * @see wicket.IInitializer#init(wicket.Application)
	 */
	public void init(Application application)
	{
		PackageResource.bind(application, TooltipPanel.class, "tooltip.js");
		
	}

}
