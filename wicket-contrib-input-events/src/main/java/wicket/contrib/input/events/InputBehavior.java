/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.input.events;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

import wicket.contrib.input.events.key.KeyType;

/**
 * Add this to your button, link whatever to create a shortcut key..
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class InputBehavior extends AbstractBehavior implements
		IHeaderContributor {

	private static ResourceReference SHORTCUTS_JAVASCRIPT = new CompressedResourceReference(
			InputBehavior.class, "shortcuts.js");

	private final List<KeyType> keyCombo;
	private EventType eventType;

	private boolean autoHook = false;
	private final TextTemplate shortcutJs = new PackagedTextTemplate(
			InputBehavior.class, "wicket-contrib-input-behavior.js");
	private final TextTemplate shortcutJsAutoHook = new PackagedTextTemplate(
			InputBehavior.class, "wicket-contrib-input-behavior-autohook.js");

	public InputBehavior(List<KeyType> keyCombo, EventType eventType) {
		this.keyCombo = keyCombo;
		this.eventType = eventType;
	}

	/**
	 * if using auto hook be sure to add this behavior last, otherwise it might
	 * not pickup the event.. Also it will only hook up to the last event if
	 * more are present (use other constructor to specify manually)
	 * 
	 * @param keyCombo
	 * @param autoHook
	 */
	public InputBehavior(List<KeyType> keyCombo, boolean autoHook) {
		this.keyCombo = keyCombo;
		this.autoHook = true;
	}

	/** The target component. */
	private Component component;

	@Override
	public void bind(Component component) {
		super.bind(component);
		this.component = component;
		component.setOutputMarkupId(true);

	}

	/**
	 * Gets the escaped DOM id that the calendar widget will get attached to.
	 * All non word characters (\W) will be removed from the string.
	 * 
	 * @return The DOM id of the calendar widget - same as the component's
	 *         markup id + 'Dp'}
	 */
	protected final String getEscapedComponentMarkupId() {
		return component.getMarkupId().replaceAll("\\W", "");

	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(SHORTCUTS_JAVASCRIPT);
		if (!autoHook) {
			response.renderJavascript(generateString(shortcutJs), null);
		}
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		if (autoHook) {
			Map<String, String> attribs = tag.getAttributes();
			for (String attrib : attribs.keySet()) {

				List<EventType> list = Arrays.asList(EventType.values());

				for (EventType e : list) {
					if (attrib.toLowerCase().contains(
							e.toString().toLowerCase())) {
						eventType = e;

					}
				}
			}

		}
	}

	@Override
	public void onRendered(Component component) {
		// TODO Auto-generated method stub
		super.onRendered(component);
		if (autoHook) {
			Response response = component.getResponse();
			response.write(generateString(shortcutJsAutoHook));

		}
	}

	private String generateString(TextTemplate textTemplate) {
		// variables for the initialization script
		Map<String, String> variables = new HashMap<String, String>();
		String widgetId = getEscapedComponentMarkupId();

		String keyComboString = "";

		boolean first = true;
		for (KeyType keyType : keyCombo) {
			if (first) {
				first = false;
			} else {

				keyComboString += "+";
			}
			keyComboString += keyType.toString();
		}
		variables.put("event", eventType.toString());
		variables.put("keys", keyComboString);
		variables.put("wicketComponentId", widgetId);
		textTemplate.interpolate(variables);
		return textTemplate.asString();

	}

}
