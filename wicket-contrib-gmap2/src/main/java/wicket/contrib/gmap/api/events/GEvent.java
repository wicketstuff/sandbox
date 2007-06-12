package wicket.contrib.gmap.api.events;

/**
 * Represents an Google Maps API's GEvent. <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GEvent">GEvent</a>
 * 
 */
public class GEvent
{
	/**
	 */
	public enum Event {
		/**
		 * 
		 */
		ADDMAPTYPE,

		/**
		 * 
		 */
		REMOVEMAPTYPE,

		/**
		 * 
		 */
		CLICK,

		/**
		 * 
		 */
		MOVESTART,

		/**
		 * 
		 */
		MOVE,

		/**
		 * 
		 */
		MOVEEND,

		/**
		 * 
		 */
		ZOOMEND,

		/**
		 * 
		 */
		MAPTYPECHANGED,

		/**
		 * 
		 */
		INFOWINDOWOPEN,

		/**
		 * 
		 */
		INFOWINDOWCLOSE,

		/**
		 * 
		 */
		ADDOVERLAY,

		/**
		 * 
		 */
		REMOVEOVERLAY,

		/**
		 * 
		 */
		CLEAROVERLAYS,

		/**
		 * 
		 */
		MOUSEOVER,

		/**
		 * 
		 */
		MOUSEOUT,

		/**
		 * 
		 */
		MOUSEMOVE,

		/**
		 * 
		 */
		DRAGSTART,

		/**
		 * 
		 */
		DRAG,

		/**
		 * 
		 */
		DRAGEND,

		/**
		 * 
		 */
		LOAD
	}
}