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
import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;

/** 
 * Wicket component to embed a GClientGeocoder, using the <a href="http://www.google.com/apis/maps/documentation/#Geocoding_HTTP_Request">Geocoding with HTTP Request</a>,
 * into your pages.
 * <p>
 * The GClientGeocoder needs a GMap2 component to project it's results. It uses the GMap key used by the GMap2 component.
 * </p>
 * 
 * @author Thijs Vonk
 */
public class GClientGeocoder extends Panel  {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Construct.
   * @param id
   * @param map The map you want the GClientGeocoder to find an address for 
   */
  public GClientGeocoder(String id, final GMap2 map, final String gMapKey)
  {
    super(id);
  
    GeocoderForm form = new GeocoderForm("form", map, gMapKey);
    form.setOutputMarkupId(true);
    add(form);
    
 }
  
 class GeocoderForm extends Form
 {   
   /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GClientGeocoderUtil geoUtil;
   private GMarker temp;
   
   
   /**
   * Construct.
   * @param id
   * @param map
   */
  public GeocoderForm(String id, final GMap2 map, final String gMapKey){     
     super(id, new CompoundPropertyModel(new GeocoderModel()));
     geoUtil = new GClientGeocoderUtil(gMapKey);
     TextField address = new TextField("address");
     address.setOutputMarkupId(true);
     add(address);
     add(new AjaxButton("submit", GeocoderForm.this){
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit(AjaxRequestTarget target, Form form) {
        GLatLng ll;
        GeocoderModel address = (GeocoderModel)form.getModelObject();
        try {
          ll = geoUtil.findAddress(address.getAddress());
          map.setCenter(ll);
          GMarkerOptions options = new GMarkerOptions();
          options.setTitle(address.getAddress());
          if(temp!=null){
            map.removeOverlay(temp);
          }
          temp = new GMarker(ll, options);
          map.addOverlay(temp);
        } catch (GMapException e) {
          target.appendJavascript("alert('Address not found, exit status"+e.getStatus()+"');");
        } catch (IOException e) {
          target.appendJavascript("alert('Address not found, exited with "+e.getMessage()+"');");
        }
      }
       
     });  
   }

 }
 
 private class GeocoderModel implements Serializable
 {
   /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String address;
   
   public GeocoderModel(){}
   
   public String getAddress(){
     return address;
   }
   
   public void setAddress(String address){
     this.address = address;
   }
 }
  
}
