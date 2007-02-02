package wicket.contrib.gmap;

import wicket.Component;
import wicket.MarkupContainer;

/**
 * Google Maps API's GMarker is represented by this class. Now you can set a
 * wicket component to be displayed within info window. The layout and the size
 * of the component is up to you, Actually the added component can be any wicket
 * component (even any container, panel or border) but not a wicket page. it can
 * be as big as you want as long as it looks good and fits into the page.
 * 
 * @param <T>
 *            Wicket component
 * 
 * @author Iulian-Corneliu Costan
 */
public class GMarker<T extends Component> extends Overlay
{
	private static final long serialVersionUID = 1L;

	private GIcon icon;

	private ComponentFactory<T> componentFactory;
	private T component;

	/**
	 * Creates an marker that will have an onClick event attached. When user
	 * clicks on this marker, wicket component created by the specified
	 * {@code componentFactory} will be rendered.
	 * 
	 * @param point
	 *            the point on the map where this marker will be anchored
	 * @param componentFactory
	 *            wicket component factory that needs to be rendered
	 * @see GPoint
	 */
	public GMarker(GPoint point, ComponentFactory<T> componentFactory)
	{
		super(point);
		this.componentFactory = componentFactory;
	}

	/**
	 * Returns the wicket component.
	 * 
	 * @param parent
	 *            The parent component. This argument must not be {@code null}.
	 * @param wicketId
	 *            The wicket id of the component. This argument must not be
	 *            {@code null}.
	 * 
	 * @throws IllegalArgumentException
	 *             Thrown if the one or both arguments are {@code null}.
	 * @return component.
	 * 
	 * @since 2.0.0
	 */
	public Component getComponent(MarkupContainer parent, String wicketId)
			throws IllegalArgumentException
	{
		if (parent == null)
		{
			throw new IllegalArgumentException("[parent] argument must not be [null].");
		}

		if (wicketId == null)
		{
			throw new IllegalArgumentException("[wicketId] argument must not be [null].");
		}

		if (component == null)
		{
			component = componentFactory.createComponent(parent, wicketId);
		}

		return component;
	}

	/**
	 * Get the name of the JavaScript function that will be called in order to
	 * create this overlay.
	 * 
	 * @return JavaScript function name
	 */
	public String getFactoryMethod()
	{
		return "createMarker" + getOverlayId();
	}
}
