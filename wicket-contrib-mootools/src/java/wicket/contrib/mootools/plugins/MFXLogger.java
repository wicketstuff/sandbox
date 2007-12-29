package wicket.contrib.mootools.plugins;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXJavascriptUtils;

public class MFXLogger extends AbstractBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MFXLogger() {
	}

	@Override
	public void bind(final Component component) {
		super.bind(component);
		component.add(new IncludeMooToolsStateless());
		component.add(HeaderContributor.forJavaScript(MFXJavascriptUtils.getMooAddons()));
	}

}
