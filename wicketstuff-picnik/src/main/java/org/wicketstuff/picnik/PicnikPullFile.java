/**
 * 
 */
package org.wicketstuff.picnik;

import java.net.URL;

/**
 * 
 * <p>
 * Created 06.04.2008 17:05:17
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
@Deprecated
public class PicnikPullFile extends PicnikFileUpload {

	private URL tempUrl;
	
	public PicnikPullFile() {
	}

	/**
	 * Get the tempUrl.
	 * @return Returns the tempUrl.
	 */
	public URL getTempUrl() {
		return tempUrl;
	}

	/**
	 * Set the tempUrl.
	 * @param tempUrl The tempUrl to set.
	 */
	public void setTempUrl(URL tempUrl) {
		this.tempUrl = tempUrl;
	}

}
