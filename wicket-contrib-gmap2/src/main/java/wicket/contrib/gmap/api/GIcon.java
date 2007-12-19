/*
 * $Id$
 * $Revision$
 * $Date$
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
package wicket.contrib.gmap.api;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import wicket.contrib.gmap.GMap2;

/**
 * Represents an Google Maps API's
 * http://code.google.com/apis/maps/documentation/reference.html#GIcon
 * 
 * @author Robert Jacolin, Gregory Maes, Vincent Demay, Anyware Technologies
 */
public class GIcon implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1714038753187423501L;

	//private String image = "http://www.google.com/mapfiles/marker.png";
	private String shadowURL = "http://www.google.com/mapfiles/shadow50.png";
	private GSize iconSize = null;
	private GSize shadowSize = null;
	private GPoint iconAnchor = null;
	private GPoint infoWindowAnchor = null;
	private GPoint infoShadowAnchor = null;
	private IModel _model = null;

	public GIcon()
	{
		
	}
	
//	public GIcon(String url)
//	{
//		image = url;
//	}

	public String getId()
	{
		return "icon" + String.valueOf(System.identityHashCode(this));
	}
	
	public void setModel(IModel model)
	{
		_model = model;
	}
	
	public IModel getModel()
	{
		return _model;
	}
	
	public String getJSadd(GMap2 map)
	{
		return "window." + getId() + "= "		
				+ getJSconstructor() + ";";
	}

	public String getJSremove(GMap2 map)
	{
		return "";
	}
	
	public void setImage(String url)
	{
		//this.image = (String) getModel().getObject();
	}
	
	public String getImage()
	{
		return (String) getModel().getObject();
	}
	
	public void setShadowURL(String url)
	{
		this.shadowURL = url;
	}
	
	public String getShadowURL()
	{
		return shadowURL;
	}

	public void setIconSize(GSize size)
	{
		this.iconSize= size;
	}
	
	public GSize getIconSize()
	{
		return iconSize;
	}
	
	public void setShadowSize(GSize size)
	{
		this.shadowSize = size;
	}
	
	public GSize getShadowSize()
	{
		return this.shadowSize;
	}
	
	public void setIconAnchor(GPoint size)
	{
		this.iconAnchor = size;
	}
	
	public GPoint getIconAnchor()
	{
		return this.iconAnchor;
	}
	
	public void setInfoWindowAnchor(GPoint size)
	{
		this.infoWindowAnchor = size;
	}
	
	public GPoint getInfoWindowAnchor()
	{
		return this.infoWindowAnchor;
	}
	
	public void setInfoShadowAnchor(GPoint size)
	{
		this.infoShadowAnchor = size;
	}
	
	public GPoint getInfoShadowAnchor()
	{
		return this.infoShadowAnchor;
	}
	
	protected String getJSconstructor()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("new GIcon();\n");
		buffer.append(getId()).append(".image = \"").append(getImage()).append("\";\n");
		buffer.append(getId()).append(".shadow = \"").append(getShadowURL()).append("\";\n");

		if (iconSize != null)
			buffer.append(getId()).append(".iconSize = ").append(getIconSize()).append(";\n");

		if (shadowSize != null)
			buffer.append(getId()).append(".shadowSize = ").append(getShadowSize()).append(
					";\n");

		if (iconAnchor != null)
			buffer.append(getId()).append(".iconAnchor = ").append(getIconAnchor()).append(
					";\n");

		if (infoWindowAnchor != null)
			buffer.append(getId()).append(".infoWindowAnchor = ").append(
			getInfoWindowAnchor()).append(";\n");

		if (infoShadowAnchor != null)
			buffer.append(getId()).append(".infoShadowAnchor = ").append(
					getInfoShadowAnchor());
		return buffer.toString();
	}
}