package wicket.contrib.gmap;

import java.util.List;

import wicket.MarkupContainer;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.list.Loop;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapContainer extends WebMarkupContainer
{

	/**
	 * Construct.
	 * 
	 * @param gmap
	 */
	public GMapContainer(MarkupContainer parent, final GMap gmap)
	{
		super(parent, ID);
		final List<Overlay> overlays = gmap.getOverlays();

		new GMapComponent(this, gmap);
		new Loop(this, "gmarkersLoop", overlays.size())
		{
			protected void populateItem(LoopItem item)
			{
				Overlay gmarker = overlays.get(item.getIteration());
				new GMarkerContainer(item, (GMarker)gmarker);
			}
		};
	}

	public static final String ID = "gmapContainer";
}
