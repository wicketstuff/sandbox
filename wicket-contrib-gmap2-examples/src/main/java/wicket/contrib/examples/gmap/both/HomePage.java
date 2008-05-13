package wicket.contrib.examples.gmap.both;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.event.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage<Void>
{

	private static final long serialVersionUID = 1L;

	private final GMap2<Object> topMap;

	private final Label<Integer> zoomLabel;

	public HomePage()
	{
		topMap = new GMap2<Object>("topPanel", GMapExampleApplication.get().getGoogleMapsAPIkey());
		topMap.addControl(GControl.GLargeMapControl);
		add(topMap);
		topMap.add(new MoveEndListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onMoveEnd(AjaxRequestTarget target)
			{
				target.addComponent(zoomLabel);
			}
		});
		zoomLabel = new Label<Integer>("zoomLabel", new PropertyModel<Integer>(topMap, "zoom"));
		zoomLabel.add(topMap.new SetZoomBehavior("onclick", 10));
		zoomLabel.setOutputMarkupId(true);
		add(zoomLabel);
	}
}
