package wicket.contrib.examples.gmap.listen;

import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLngBounds;
import wicket.contrib.gmap.event.LoadListener;
import wicket.contrib.gmap.event.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;
	
	private Label zoomLabel;

	private MultiLineLabel boundsLabel;

	public HomePage() {
		final GMap2 map = new GMap2("map", LOCALHOST);
		map.addControl(GControl.GLargeMapControl);
		add(map);
		map.add(new MoveEndListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onMoveEnd(AjaxRequestTarget target)
			{
				target.addComponent(zoomLabel);
				target.addComponent(boundsLabel);
			}
		});
		map.add(new LoadListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onLoad(AjaxRequestTarget target) {
				target.addComponent(boundsLabel);
			}
		});
		
		zoomLabel = new Label("zoom", new PropertyModel(map, "zoom"));
		zoomLabel.setOutputMarkupId(true);
		add(zoomLabel);
		
		boundsLabel = new MultiLineLabel("bounds", new PropertyModel(map, "bounds")) {
			@Override
			public IConverter getConverter(Class type) {
				return new IConverter() {
					public Object convertToObject(String value, Locale locale) {
						throw new UnsupportedOperationException();
					}
					public String convertToString(Object value, Locale locale) {
						GLatLngBounds bounds = (GLatLngBounds)value;
						
						StringBuffer buffer = new StringBuffer();
						buffer.append("NE (");
						buffer.append(bounds.getNE().getLat());
						buffer.append(",");
						buffer.append(bounds.getNE().getLng());
						buffer.append(")\nSW (");
						buffer.append(bounds.getSW().getLat());
						buffer.append(",");
						buffer.append(bounds.getSW().getLng());
						buffer.append(")");
						return buffer.toString();
					}
				};
			}
		};
		boundsLabel.setOutputMarkupId(true);
		add(boundsLabel);
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
