package wicket.contrib.gmap.api;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.IJavascriptResponse;

/**
 * Represents an Google Maps API's GMarker
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GMarker">GMarker</a>
 *
 */
public class GMarker<T extends Component> extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private GLatLng point;

	/**
	 * @param point
	 *            the point on the map where this marker will be anchored
	 * @param componentFactory
	 *            wicket component factory that needs to be rendered
	 */
	public GMarker(GLatLng point)
	{
		super();
		this.point = point;
	}

	@Override
	public String getJSConstructor()
	{
		return "new GMarker(" + point.getJSConstructor() + ")";
	}

	/**
	 * Provides an AjaxRequestTarget.IListener providing JavaScript code to call the
	 * addOverlay method on the GMap. This is typically used by Listeners on the
	 * client programmers side to be able to add this GMarker as an Overlay within
	 * a AjaxCallCycle.
	 */
	public AjaxRequestTarget.IListener getJSAdd()
	{
		return new AjaxRequestTarget.IListener()
		{

			public void onBeforeRespond(Map map, AjaxRequestTarget target)
			{
			}

			public void onAfterRespond(Map map, IJavascriptResponse response)
			{
				response.addJavascript("addOverlay('"
						+ GMarker.this.getGMap2().getGMap2Panel().getMarkupId() + "', '"
						+ GMarker.this.hashCode() + "', '" + GMarker.this.getJSConstructor() + "')");
			}
		};
	}
}
