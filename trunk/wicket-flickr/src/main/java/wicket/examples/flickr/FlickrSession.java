package wicket.examples.flickr;

import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

public class FlickrSession extends WebSession {
	/** used for serialization. */
	private static final long serialVersionUID = 3946644471811858975L;

	/** The user supplied Flickr api key. */
	private String flickrApiKey;

	/** Constructor. */
	protected FlickrSession(WebApplication application) {
		super(application);
	}

	/**
	 * @return Returns the flickrApiKey.
	 */
	public String getFlickrApiKey() {
		return flickrApiKey;
	}

	/**
	 * @param flickrApiKey The flickrApiKey to set.
	 */
	public void setFlickrApiKey(String flickrApiKey) {
		this.flickrApiKey = flickrApiKey;
	}
}
