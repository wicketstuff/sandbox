package wicket.contrib.mootools.plugins;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;
import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MooBase;

public class MFXSmoothScroll extends AbstractRequireMooStatelessBehavior {
	private static final long serialVersionUID = 1L;
	private int duration = 0;

	public MFXSmoothScroll() {
	}

	@Override
	public void bind(final Component component) {
		super.bind(component);
		component.add(new IncludeMooToolsStateless());
	}

	public MFXSmoothScroll(final int duration) {
		this.duration = duration;
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		addMooDomFunction(mooFunction());
		super.renderHead(response);
	}

	@Override
	public String mooFunction() {

		if (Application.get().getMetaData(MooBase.USE_TRUNK_MOOTOOLS) != null) {
			if ((Boolean) Application.get().getMetaData(MooBase.USE_TRUNK_MOOTOOLS)) {
				return "new SmoothScroll({duration: 1000,offset: {'x': 0, 'y': 0},transition: Fx.Transitions.Quad.easeInOut},document.body);";
			}
		}

		if (duration != 0) {
			return "new SmoothScroll(" + duration + ");";
		} else {
			return "new SmoothScroll();";
		}
	}
}
