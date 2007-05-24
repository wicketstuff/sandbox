package org.wicketstuff.scriptaculous;

import java.util.Map;

import org.apache.wicket.util.string.JavascriptUtils;
import org.apache.wicket.util.template.TextTemplate;
import org.apache.wicket.util.template.TextTemplateDecorator;

/**
 * Text template that will automatically add Prototype function for adding onLoad behavior.
 * This allows for any javascript to be un-obtrusive.
 * 
 * Make sure that component using this template 
 * {@link ScriptaculousAjaxBehavior#newJavascriptBindingBehavior() loads the prototype javascript libraries} 
 * or extends from the {@link ScriptaculousAjaxBehavior proper class}.
 * 
 * @see http://www.prototypejs.org/api/event/observe
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class OnLoadJavascriptTemplate extends TextTemplateDecorator {

	public OnLoadJavascriptTemplate(TextTemplate textTemplate) {
		super(textTemplate);
	}

	public String getAfterTemplateContents() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(JavascriptUtils.SCRIPT_OPEN_TAG)
		.append("Event.observe(window, 'load', function() {").append("\n");
		return buffer.toString();
	}

	public String getBeforeTemplateContents() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n")
		.append("});")
		.append(JavascriptUtils.SCRIPT_CLOSE_TAG);
		return buffer.toString();
	}

	public TextTemplate interpolate(Map variables) {
		return this;
	}
}
