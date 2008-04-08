/**
 * 
 */
package org.wicketstuff.picnik.api;

/**
 * Class storing your Picnik API key.
 * <p>
 * Created 29.03.2008 23:19:09
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class GeneralSettings extends AbstractSettings {

	private String apiKey;

	@Override
	public GeneralSettings fillParameters() {
		addParam("_apikey", getApiKey());
		return this;
	}

	/**
	 * Get the apiKey.
	 * @return Returns the apiKey.
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Set the apiKey. _apikey: identifies your application to Picnik's servers
	 * @see http://www.picnik.com/info/api/reference/_apikey
	 * @param apiKey The apiKey to set.
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
