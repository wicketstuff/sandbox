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
import java.util.StringTokenizer;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import wicket.contrib.gmap.api.GLatLng;

/**
 * GClientGeocoderUtil 
 * 
 * @author Thijs Vonk
 */

public class GClientGeocoderUtil {

  // Constants
  public static final String OUTPUT_CSV = "csv";

  public static final String OUTPUT_XML = "xml";

  public static final String OUTPUT_KML = "kml";

  public static final String OUTPUT_JSON = "json";

  private HttpClient httpClient;

  private String gMapKey;

  private String output = OUTPUT_CSV;


  /**
   * @param gMapKey
   *          Gmap API key
   * @throws IllegalArgumentException
   *           If the API key is <code>null</code>
   */
  public GClientGeocoderUtil(String gMapKey) {
    this.gMapKey = gMapKey;

    if (gMapKey == null) {
      throw new IllegalArgumentException("API key cannot be null");
    }

    httpClient = new HttpClient();
    HttpClientParams httpClientParams = new HttpClientParams();
    DefaultHttpMethodRetryHandler defaultHttpMethodRetryHandler = new DefaultHttpMethodRetryHandler(
        0, false);
    httpClientParams.setParameter(HttpClientParams.RETRY_HANDLER,
        defaultHttpMethodRetryHandler);
    httpClient.setParams(httpClientParams);
  }


 /**
   * @param address
   *          The address for which a coordinate must be found.
   * @return GLatLng point for the address found by the Geocoder
   * @throws GMapException
   *           If a error happened on the side of Google
   * @throws IOException
   *           If a connection error happened
   */
  public GLatLng findAddress(String address) throws GMapException,
      IOException {

    address = address.replace(' ', '+');

    String geocoderUrl = "http://maps.google.com/maps/geo?q=" + address
        + "&output=" + output + "&key=" + gMapKey;
    PostMethod post = new PostMethod(geocoderUrl);
    post.addRequestHeader("Content-Type",
        "application/x-www-form-urlencoded; charset=utf-8");
    String result;
    try {
      httpClient.executeMethod(post);
      result = post.getResponseBodyAsString();

    } catch (IOException e) {
      throw e;
    }
    return parseCSVResponse(result);
  }

  /**
   * @param result
   * @return GLatLng point for the address found by the Geocoder
   * @throws GMapException thrown when the Geocoding returned a status other the GMapException.G_GEO_SUCCESS
   */
  private GLatLng parseCSVResponse(String result) throws GMapException {
    StringTokenizer gLatLng = new StringTokenizer(result, ",");
    String status = gLatLng.nextToken();
    gLatLng.nextToken(); // This is the precision, we do not use this now.
    String latitude = gLatLng.nextToken();
    String longitude = gLatLng.nextToken();

    if (Integer.parseInt(status) != GMapException.G_GEO_SUCCESS) {
      throw new GMapException(Integer.parseInt(status));
    }

    return new GLatLng(Double.parseDouble(latitude), 
        Double.parseDouble(longitude));
  }
}
