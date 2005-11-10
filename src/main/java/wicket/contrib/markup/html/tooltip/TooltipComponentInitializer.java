package wicket.contrib.markup.html.tooltip;

import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;

public class TooltipComponentInitializer implements IInitializer
{

	public void init(Application application)
	{
		PackageResource.bind(application, Tooltip.class, "tooltip.js");
		
	}

}
