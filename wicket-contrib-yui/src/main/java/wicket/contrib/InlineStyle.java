package wicket.contrib;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InlineStyle implements Serializable {

	private static final long serialVersionUID = 1L;

	// valid Styles....
	Map<String, String> styleMap = new HashMap<String, String>();

	/**
	 * Construct.
	 */
	public InlineStyle() {
	}

	/**
	 * Construct.
	 * 
	 * @param map
	 */
	public InlineStyle(Map<String, String> map) {
		this.styleMap.putAll(map);
	}

	/**
	 * @returns a ":" separated String to be used a an Inline Style
	 * 
	 */

	public String getStyle() {

		final StringBuffer buffer = new StringBuffer();
		for (final Map.Entry<String, String> entry : styleMap.entrySet()) {
			final String value = entry.getValue();
			if (value != null) {
				buffer.append(entry.getKey());
				buffer.append(" : ");
				buffer.append(value);
				buffer.append(" ; ");
			}
		}
		return buffer.toString();
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
			styleMap.put(element, value);
		}
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
		return true;
	}

}
