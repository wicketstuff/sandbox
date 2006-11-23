package wicket.contrib.markup.html.form.sliders;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

public class DojoIntegerSliderHandler extends AbstractRequireDojoBehavior
{

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.Slider");
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING FOR THE MOMENT
	}

}
