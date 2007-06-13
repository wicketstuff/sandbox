package wicket.contrib.gmap.api;

/**
 * Root class for any class that represents a GMap JavaScript class or object.
 */
public abstract class GMapApi
{
	/**
	 * @return A JavaScript constructor that represents this element.
	 */
	public abstract String getJSConstructor();
}
