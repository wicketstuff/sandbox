package org.wicketstuff.yui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO : move to helper
 * @author josh
 *
 */
public class InlineStyle implements Serializable {

	// valid Styles....
	private static final long serialVersionUID = 1L;

	Map<String, String> styleMap = new HashMap<String, String>();

	/**
	 * 
	 * 
	 */
	public InlineStyle() {
	}

	/**
	 * 
	 * @param map
	 */
	public InlineStyle(Map<String, String> map) {
		this.styleMap.putAll(map);
	}

	/**
	 * adds a style element with teh value... TODO : check for valid element
	 * string and value ? override?
	 * 
	 * @param element
	 * @param value
	 */

	public void add(String element, String value) {

		if (isValid(element, value)) {
			// if (styleMap.get(element) != null) {
			// }
			styleMap.put(element, value);
		}
	}

	/**
	 * @returns a ":" separated String to be used a an Inline Style
	 * 
	 */

	public String getStyle() {

		final StringBuffer buffer = new StringBuffer();
		for (Map.Entry<String, String> entry : styleMap.entrySet()) {
			final String value = entry.getValue();
			if (value != null) {
				buffer.append(entry.getKey());
				buffer.append(":");
				buffer.append(value);
				buffer.append(";");
			}
		}
		return buffer.toString();
	}

	/**
	 * validate is this style is valid for this element value... now it just
	 * returns true all the time...
	 * 
	 * @param element
	 * @param value
	 * @return
	 */
	private boolean isValid(String element, String value) {
		// TODO Auto-generated method stub
		return true;
	}

	public void add(String element, int int_value)
	{
		this.add(element, Integer.toString(int_value));
	}
}
