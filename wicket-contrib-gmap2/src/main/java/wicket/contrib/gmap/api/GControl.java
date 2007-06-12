package wicket.contrib.gmap.api;

/**
 * Represents an Google Maps API's GControl. <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GControl">GControl</a>
 * 
 */
public enum GControl {
	GSmallMapControl, GLargeMapControl, GSmallZoomControl, GScaleControl, GMapTypeControl;
	
	public String getJSConstructor() {
		return "new " + this.toString() + "()";
	}
}
