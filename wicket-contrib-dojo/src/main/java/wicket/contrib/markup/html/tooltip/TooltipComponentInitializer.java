package wicket.contrib.markup.html.tooltip;

import wicket.Application;
import wicket.IInitializer;
import wicket.contrib.dojo.markup.html.tooltip.DojoTooltip;
import wicket.markup.html.PackageResource;

/**
 * @author jcompagner
 * @deprecated see {@link DojoTooltip}
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
