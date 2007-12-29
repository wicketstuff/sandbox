package wicket.contrib.mootools.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.util.time.Duration;

public abstract class MFXFadeOutBehavior extends AjaxEventBehavior {
	private String parentId = "none";
	private Duration duration;
	private Component component;

	public MFXFadeOutBehavior(final String event) {
		this(event, Duration.seconds(1));
	}

	public MFXFadeOutBehavior(final String event, final Duration duration) {
		this(event, duration, null);
	}

	public MFXFadeOutBehavior(final String event, final Duration duration, final Component component) {
		super(event);
		this.duration = duration;
		this.component = component;
	}

	@Override
	public void beforeRender(final Component component) {
		super.beforeRender(component);
		if (this.component == null) {
			component.setOutputMarkupId(true);
			parentId = component.getMarkupId();
		} else {
			this.component.setOutputMarkupId(true);
			parentId = this.component.getMarkupId();
		}

	}

	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new IAjaxCallDecorator() {
			private static final long serialVersionUID = 1L;

			public CharSequence decorateOnFailureScript(final CharSequence arg0) {
				return arg0;
			}

			public CharSequence decorateOnSuccessScript(final CharSequence arg0) {
				return arg0;
			}

			public CharSequence decorateScript(final CharSequence arg0) {
				return "new Fx.Tween('" + parentId + "','opacity',{ duration:" + duration.getMilliseconds()
						+ ", onComplete: function() { " + arg0 + "} }).start(1.0,0.0); ";
			}

		};
	}

	private static final long serialVersionUID = 1L;
}
