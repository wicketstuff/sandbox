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

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.util.Geocoder;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * HttpClientGeocoder
 * 
 * @author Thijs Vonk
 */

public class HttpClientGeocoder {

	private HttpClient httpClient;

	private Geocoder geocoder;

	/**
	 * @param gMapKey
	 *            Gmap API key
	 * @throws IllegalArgumentException
	 *             If the API key is <code>null</code>
	 */
	public HttpClientGeocoder(String gMapKey) {
		if (gMapKey == null) {
			throw new IllegalArgumentException("API key cannot be null");
		}

		this.geocoder = new Geocoder(gMapKey);

		this.httpClient = new HttpClient();

		HttpClientParams httpClientParams = new HttpClientParams();
		DefaultHttpMethodRetryHandler defaultHttpMethodRetryHandler = new DefaultHttpMethodRetryHandler(
				0, false);
		httpClientParams.setParameter(HttpClientParams.RETRY_HANDLER,
				defaultHttpMethodRetryHandler);
		this.httpClient.setParams(httpClientParams);
	}

	/**
	 * @param address
	 *            The address for which a coordinate must be found.
	 * @return GLatLng point for the address found by the Geocoder
	 * @throws GeocoderException
	 *             If a error happened on the side of Google
	 * @throws IOException
	 *             If a connection error happened
	 */
	public GLatLng findAddress(String address) throws GeocoderException,
			IOException {

		PostMethod post = new PostMethod(geocoder.encode(address));
		post.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");

		httpClient.executeMethod(post);

		String response = post.getResponseBodyAsString();

		return geocoder.decode(response);
	}
}
