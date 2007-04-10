package org.apache.wicket.contrib.markup.html.tooltip;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.contrib.dojo.markup.html.tooltip.DojoTooltip;
import org.apache.wicket.markup.html.PackageResource;

/**
 * @author jcompagner
 * @deprecated will be remove in 2.0 use {@link DojoTooltip}
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
