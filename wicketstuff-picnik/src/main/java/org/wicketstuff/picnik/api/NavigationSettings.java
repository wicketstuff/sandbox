/**
 * 
 */
package org.wicketstuff.picnik.api;

/**
 * 
 * <p>
 * Created 30.03.2008 00:11:01
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class NavigationSettings extends AbstractSettings {

	// _default_in: default "Photos" subtab (see http://www.picnik.com/info/api/reference/_default_in)
	private String defaultIn;

	// _default_out: default "Save & Share" subtab (see http://www.picnik.com/info/api/reference/_default_out)
	private String defaultOut;

	// _page: starting point within the Picnik UI (see http://www.picnik.com/info/api/reference/_page)
	private String page;

	@Override
	public NavigationSettings fillParameters() {
		addParam("_default_in", getDefaultIn());
		addParam("_default_out", getDefaultOut());
		addParam("_page", getPage());
		return this;
	}

	/**
	 * Get the defaultIn.
	 * @return Returns the defaultIn.
	 */
	public String getDefaultIn() {
		return defaultIn;
	}

	/**
	 * Set the defaultIn.
	 * _default_in: default "Photos" subtab 
	 * @see http://www.picnik.com/info/api/reference/_default_in
	 * @param defaultIn The defaultIn to set.
	 */
	public void setDefaultIn(String defaultIn) {
		this.defaultIn = defaultIn;
	}

	/**
	 * Get the defaultOut.
	 * @return Returns the defaultOut.
	 */
	public String getDefaultOut() {
		return defaultOut;
	}

	/**
	 * Set the defaultOut.
	 * _default_out: default "Save & Share" subtab 
	 * @see http://www.picnik.com/info/api/reference/_default_out
	 * @param defaultOut The defaultOut to set.
	 */
	public void setDefaultOut(String defaultOut) {
		this.defaultOut = defaultOut;
	}

	/**
	 * Get the page.
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Set the page.
	 * _page: starting point within the Picnik UI 
	 * @see http://www.picnik.com/info/api/reference/_page
	 * @param page The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}


}
