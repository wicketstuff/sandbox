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
 * 
 */
public class AnimGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private List<AnimOption> animOptionList;

	private String easing;

	private double duration;

	private int maxSelection;

	private String selectedValues = "";

	private String valueId;

	/**
	 * Creates an AnimGroup
	 * 
	 * @param id -
	 *            wicket id
	 * @param settings -
	 *            defines the animation settings
	 */
	public AnimGroup(String id, AnimSettings settings, String valueId) {
		super(id);

		this.animOptionList = settings.getAnimOptionList();
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.maxSelection = settings.getMaxSelection();
		this.valueId = valueId;

		for (int i = 0; i < animOptionList.size(); i++) {
			AnimOption animSelectOption = (AnimOption) animOptionList
					.get(i);
			String value = animSelectOption.getSelectedValue();
			if (selectedValues.equals("")) {
				selectedValues = "'" + value + "'";
			} else {
				selectedValues = selectedValues + ",'" + value + "'";
			}
		}

		Label initialization = new Label("init", new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

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
		Map<Object, Object> variables = new HashMap<Object, Object>(7);
		variables.put("javaScriptId", javaScriptId);
		variables.put("easing", "YAHOO.util.Easing." + easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(animOptionList.size()));
		variables.put("selectedValues", selectedValues);
		variables.put("valueId", "'" + valueId + "'");
		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id on attach
	 */
	protected void onAttach() {
		super.onAttach();
		javaScriptId = getMarkupId();
	}

	/**
	 * 
	 */
	protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		super.onComponentTagBody(markupStream, openTag);		
//		 StringBuffer buffer = new StringBuffer();
//		 buffer.append("\n<script type=\"text/javascript\">")
//		 .append(getJavaScriptComponentInitializationScript())
//		 .append("\n</script>\n");
//		 replaceComponentTagBody(markupStream, openTag, buffer.toString());
	}

	/**
	 * Get the animOptionList
	 * 
	 * @return the animOptionList
	 */
	public List<AnimOption> getAnimOptionList() {
		return animOptionList;
	}

	/**
	 * Set the animOptionList
	 * 
	 * @param animOptionList
	 *            the animOptionList to set
	 */
	public void setAnimOptionList(List<AnimOption> animOptionList) {
		this.animOptionList = animOptionList;
	}

	/**
	 * Get the duration
	 * 
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Set the duration
	 * 
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Get the easing
	 * 
	 * @return the easing
	 */
	public String getEasing() {
		return easing;
	}

	/**
	 * Set the easing
	 * 
	 * @param easing
	 *            the easing to set
	 */
	public void setEasing(String easing) {
		this.easing = easing;
	}

	/**
	 * Get the JavaScriptId
	 * 
	 * @return the javaScriptId
	 */
	public String getJavaScriptId() {
		return javaScriptId;
	}

	/**
	 * Set the javaScriptId
	 * 
	 * @param javaScriptId
	 *            the javaScriptId to set
	 */
	public void setJavaScriptId(String javaScriptId) {
		this.javaScriptId = javaScriptId;
	}

	/**
	 * Get the maximum selection allowed
	 * 
	 * @return the maxSelection
	 */
	public int getMaxSelection() {
		return maxSelection;
	}

	/**
	 * Set the maximum selections allowed
	 * 
	 * @param maxSelection
	 *            the maxSelection to set
	 */
	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	/**
	 * Get the selected values
	 * 
	 * @return the selectedValues
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * Set the selected values
	 * 
	 * @param selectedValues
	 *            the selectedValues to set
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * Get the value id
	 * 
	 * @return the valueId
	 */
	public String getValueId() {
		return valueId;
	}

	/**
	 * Set the value id
	 * 
	 * @param valueId
	 *            the valueId to set
	 */
	public void setValueId(String valueId) {
		this.valueId = valueId;
	}


}
