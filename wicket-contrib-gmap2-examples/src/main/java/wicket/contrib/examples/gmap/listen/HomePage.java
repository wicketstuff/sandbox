package wicket.contrib.examples.gmap.listen;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.event.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;
	
	private Label zoomLabel;

	public HomePage() {
		final GMap2 topMap = new GMap2("topPanel", LOCALHOST);
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
		zoomLabel = new Label("zoomLabel", new PropertyModel(topMap, "zoom"));
		zoomLabel.setOutputMarkupId(true);
		add(zoomLabel);
	}

	/**
	 * pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info. Also the GClientGeocoder is pickier on this than
	 * the GMap2. Running on 'localhost' GMap2 will ignore the key and the maps
	 * will show up, but GClientGeocoder wount. So if the key doesnt match the
	 * url down to the directory GClientGeocoder will not work.
	 * 
	 * This key is good for all URLs in this directory:
	 * http://localhost:8080/wicket-contrib-gmap2-examples/gmap2/
	 */
	private static final String LOCALHOST = "ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xQRS2YPSd8S9D1NKPBvdB1fr18_CxR-svEYj6URCf5QDFq3i03mqrDlbA";
}
