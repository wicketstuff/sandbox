package wicket.contrib.mootools.plugins;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;
import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MooBase;

public class MFXSmoothScroll extends AbstractRequireMooStatelessBehavior {
	private static final long serialVersionUID = 1L;
	private Duration duration;

	public MFXSmoothScroll() {
		this(Duration.milliseconds(1000));
	}

	public MFXSmoothScroll(final Duration duration) {
		this.duration = duration;
	}

	@Override
	public void bind(final Component component) {
		super.bind(component);
		component.add(new IncludeMooToolsStateless());
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
				return "new SmoothScroll({duration: " + duration.getMilliseconds()
						+ ",offset: {'x': 0, 'y': 0},transition: Fx.Transitions.Quad.easeInOut},document.body);";
			}
		}

		return "new SmoothScroll(" + duration.getMilliseconds() + ");";
	}
}
