package wicket.contrib.gmap;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.Loop;

import java.util.List;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapContainer extends WebMarkupContainer
{

    /**
     * Construct.
     * @param gmap
     */
    public GMapContainer(final GMap gmap)
    {
        super(ID);
        final List<Overlay> overlays = gmap.getOverlays();
        add(new Loop("gmarkersLoop", overlays.size())
        {
            protected void populateItem(LoopItem item)
            {
                Overlay gmarker = overlays.get(item.getIteration());
                item.add(new GMarkerContainer((GMarker) gmarker));
            }
        });
    }

    public static final String ID = "gmapContainer";
}
