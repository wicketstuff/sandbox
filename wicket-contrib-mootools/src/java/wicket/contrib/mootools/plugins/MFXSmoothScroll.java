package wicket.contrib.mootools.plugins;

import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;

public class MFXSmoothScroll extends AbstractRequireMooStatelessBehavior {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void renderHead(IHeaderResponse response) {
		addMooDomFunction(mooFunction());
		super.renderHead(response);
	}
	
	@Override
	public String mooFunction() {
		return "new SmoothScroll();";
	}

}
