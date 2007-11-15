package wicket.contrib.examples.gmap.geocode;

import java.io.IOException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GClientGeocoderBehavior;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	private ServerGeocoder geocoder = new ServerGeocoder(LOCALHOST);

	public HomePage() {
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		final GMap2 bottomMap = new GMap2("bottomPanel",
				new GMapHeaderContributor(LOCALHOST));
		bottomMap.setOutputMarkupId(true);
		bottomMap.setMapType(GMapType.G_SATELLITE_MAP);
		bottomMap.setScrollWheelZoomEnabled(true);
		bottomMap.addControl(GControl.GSmallMapControl);
		add(bottomMap);

		Form geocodeForm = new Form("geocoder");
		add(geocodeForm);

		final TextField addressTextField = new TextField("address", new Model(""));
		geocodeForm.add(addressTextField);

		Button button = new Button("client");
		//Using GClientGeocoder the geocoding request
		//is performed on the client using JavaScript 
		button.add(new GClientGeocoderBehavior("onclick", addressTextField,
				LOCALHOST) {
			@Override
			public void onGeoCode(AjaxRequestTarget target, int status,
					String address, GLatLng latLng) {
				if (status == GeocoderException.G_GEO_SUCCESS) {
					bottomMap.getInfoWindow().open(
							latLng,
							new GInfoWindowTab(address, new Label(address,
									address)));
				} else {
					error("Unable to geocode (" + status + ")");
					target.addComponent(feedback);
				}
			};
		});
		geocodeForm.add(button);
		
		//Using ServerGeocoder the geocoding request
		//is performed on the server using Googles HTTP interface.
		//http://www.google.com/apis/maps/documentation/services.html#Geocoding_Direct
		geocodeForm.add(new AjaxButton("server", geocodeForm) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				try {
					String address = addressTextField.getModelObjectAsString();
					
					GLatLng latLng = geocoder.findAddress(address);

					bottomMap.getInfoWindow().open(
							latLng,
							new GInfoWindowTab(address, new Label(address,
									address)));
				} catch (IOException e) {
					target
							.appendJavascript("Unable to geocode (" + e.getMessage() + ")");
				}
			}
		});
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
	 * http://localhost:8080/wicket-contrib-gmap2-examples/geocode/
	 */
	private static final String LOCALHOST = "ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xSapMiGcQyewhl3lbt4-4vkCWRSihSj0D95fir_CKI1jrXtSFcyTfUvXQ";
}
