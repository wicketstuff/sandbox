/**
 * 
 */
package org.wicketstuff.picnik.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.util.string.Strings;

/**
 * 
 * <p>
 * Created 30.03.2008 00:04:04
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class AppearanceSettings extends AbstractSettings {

	// _exclude: UI components to exclude (see http://www.picnik.com/info/api/reference/_exclude)
	private Set<Exclude> exclude = new HashSet<Exclude>();

	// _host_name: the user-readable name of your site (see
	// http://www.picnik.com/info/api/reference/_host_name)
	private String hostName;

	public enum Exclude {
		in, edit, create, out
	}

	@Override
	public AppearanceSettings fillParameters() {
		addParam("_exclude", exclude, ",");
		addParam("_host_name", getHostName());
		return this;
	}

	/**
	 * Set and replace the excludes. _exclude: UI components to exclude
	 * @see http://www.picnik.com/info/api/reference/_exclude
	 * @param excludes
	 */
	public void setExclude(Exclude... excludes) {
		exclude.clear();
		Collections.addAll(exclude, excludes);
	}

	/**
	 * Set the hostName. _host_name: the user-readable name of your site
	 * @see http://www.picnik.com/info/api/reference/_host_name
	 * @param hostName The hostName to set.
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * Get the hostName.
	 * @return Returns the hostName.
	 */
	public String getHostName() {
		return hostName;
	}
}
