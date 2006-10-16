package wicket.contrib.markup.html.yui.anim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wicket.Component;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.model.AbstractReadOnlyModel;

/**
 * An AnimGroup groups the options
 * 
 * @author cptan
 */
public class AnimGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private List<AnimOption> animSelectOptionList;

	private double duration;

	private String easing;

	private String javaScriptId;

	private int maxSelection;

	private String selectedValues = "";

	/**
	 * Creates an AnimGroup
	 * 
	 * @param id -
	 *            wicket id
	 * @param settings -
	 *            defines the animation settings
	 */
	public AnimGroup(String id, AnimSettings settings) {
		super(id);

		this.animSelectOptionList = settings.getAnimOptionList();
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.maxSelection = settings.getMaxSelection();

		for (int i = 0; i < animSelectOptionList.size(); i++) {
			AnimOption animSelectOption = animSelectOptionList.get(i);
			String value = animSelectOption.getSelectedValue();
			if (selectedValues.equals("")) {
				selectedValues = "'" + value + "'";
			} else {
				selectedValues = selectedValues + ",'" + value + "'";
			}
		}

		Label initialization = new Label("init", new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject(Component component) {
				return getJavaScriptComponentInitializationScript();
			}
		});
		initialization.setEscapeModelStrings(false);
		add(initialization);

	}

	/**
	 * Initialize the init.js which is shared among a group of options
	 * 
	 * @return a String representation of the init.js
	 */
	protected String getJavaScriptComponentInitializationScript() {
		PackagedTextTemplate template = new PackagedTextTemplate(
				AnimGroup.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(6);
		variables.put("javaScriptId", javaScriptId);
		variables.put("easing", "YAHOO.util.Easing." + easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(animSelectOptionList.size()));
		variables.put("selectedValues", selectedValues);
		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id on attach
	 */
	@Override
	protected void onAttach() {
		super.onAttach();
		javaScriptId = getMarkupId();
	}

	/**
	 * 
	 */
	@Override
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {
		super.onComponentTagBody(markupStream, openTag);
		// StringBuffer buffer = new StringBuffer();
		// buffer.append("\n<script type=\"text/javascript\">")
		// .append(getJavaScriptComponentInitializationScript())
		// .append("\n</script>\n");
		// replaceComponentTagBody(markupStream, openTag, buffer.toString());
	}

}
