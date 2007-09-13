/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package wicket.contrib.examples.gmap.geocode;

import java.io.IOException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * Wicket component to embed a GClientGeocoder, using the <a
 * href="http://www.google.com/apis/maps/documentation/#Geocoding_HTTP_Request">Geocoding
 * with HTTP Request</a>, into your pages.
 * <p>
 * The GClientGeocoder needs a GMap2 component to project it's results. It uses
 * the GMap key used by the GMap2 component.
 * </p>
 * 
 * @author Thijs Vonk
 */
public class GeocoderForm extends Form {

	private static final long serialVersionUID = 1L;

	private HttpClientGeocoder geocoder;

	private String address;

	private GMarker marker;

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param map
	 */
	public GeocoderForm(String id, final GMap2 map, final String gMapKey) {
		super(id);

		geocoder = new HttpClientGeocoder(gMapKey);

		TextField addressTextField = new TextField("address",
				new PropertyModel(this, "address"));
		addressTextField.setOutputMarkupId(true);
		add(addressTextField);

		add(new AjaxButton("submit", GeocoderForm.this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				try {
					GLatLng latLng = geocoder.findAddress(address);

					if (marker != null) {
						map.removeOverlay(marker);
					}

					map.setCenter(latLng);

					GMarkerOptions options = new GMarkerOptions();
					options.setTitle(address);
					marker = new GMarker(latLng, options);
					map.addOverlay(marker);
				} catch (GeocoderException e) {
					target
							.appendJavascript("alert('Address not found, exit status"
									+ e.getStatus() + "');");
				} catch (IOException e) {
					target
							.appendJavascript("alert('Address not found, exited with "
									+ e.getMessage() + "');");
				}
			}
		});
	}
}