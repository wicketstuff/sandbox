package wicket.contrib.markup.html.form.validation;

import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;

/**
 * Innitializer class for FXFeedbackIndicator
 * 
 * @author Marco van de Haar
 */
public class FXFeedbackIndicatorInitializer implements IInitializer
{

	/* (non-Javadoc)
	 * @see wicket.IInitializer#init(wicket.Application)
	 */
	public void init(Application application)
	{
		PackageResource.bind(application, FXFeedbackIndicator.class, "alert.gif");		
	}

}
