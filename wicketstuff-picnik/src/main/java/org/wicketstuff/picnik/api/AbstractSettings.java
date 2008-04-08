/**
 * 
 */
package org.wicketstuff.picnik.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.util.string.Strings;
import org.wicketstuff.picnik.api.AppearanceSettings.Exclude;

/**
 * 
 * <p>Created 30.03.2008 13:23:49</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public abstract class AbstractSettings implements Serializable {

	private final Map<String, String> params = new HashMap<String, String>();
	
	/**
	 * Put object state into parameter Map.
	 * @param params
	 */
	public abstract AbstractSettings fillParameters();
	
	/**
	 * Convinience method for adding a parameter.
	 * If value is empty or null, nothing is added.
	 * @param params
	 * @param name
	 * @param value
	 */
	protected final void addParam(String name, String value) {
		if (Strings.isEmpty(value)) {
			return;
		}
		params.put(name, value);
	}
	
	protected final void addParam(String name, Object value) {
		addParam(name, value == null ? null : value.toString());
	}
	
	protected final void addParam(String name, Collection<? extends Object> col, String seperator) {
		String[] objectStrings = new String[col.size()];
		int i = 0;
		for (Object ex : col) {
			objectStrings[i++] = ex.toString();
		}
		addParam(name, Strings.join(seperator, objectStrings));		
	}
	
	/**
	 * Get the params.
	 * @return
	 */
	public final Map<String, String> getParams() {
		return this.params;
	}
}
