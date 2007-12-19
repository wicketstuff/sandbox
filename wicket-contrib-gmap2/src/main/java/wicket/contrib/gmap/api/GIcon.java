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

	private String image = "http://www.google.com/mapfiles/marker.png";
	private String shadowURL = "http://www.google.com/mapfiles/shadow50.png";
	private GSize iconSize = null;
	private GSize shadowSize = null;
	private GPoint iconAnchor = null;
	private GPoint infoWindowAnchor = null;
	private GPoint infoShadowAnchor = null;

	public GIcon()
	{
		
	}
	
	public GIcon(String url)
	{
		image = url;
	}

	public String getId()
	{
		return "icon" + String.valueOf(System.identityHashCode(this));
	}
	
	public void setImage(String url)
	{
		this.image = url;
	}
	
	public String getImage()
	{
		return image;
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
		buffer.append("(function() {\n");
		buffer.append("var icon = new GIcon();\n");
		buffer.append("icon.image = \"").append(getImage()).append("\";\n");
		buffer.append("icon.shadow = \"").append(getShadowURL()).append("\";\n");

		if (iconSize != null)
			buffer.append("icon.iconSize = ").append(getIconSize()).append(";\n");

		if (shadowSize != null)
			buffer.append("icon.shadowSize = ").append(getShadowSize()).append(
					";\n");

		if (iconAnchor != null)
			buffer.append("icon.iconAnchor = ").append(getIconAnchor()).append(
					";\n");

		if (infoWindowAnchor != null)
			buffer.append("icon.infoWindowAnchor = ").append(
			getInfoWindowAnchor()).append(";\n");

		if (infoShadowAnchor != null)
			buffer.append("icon.infoShadowAnchor = ").append(
					getInfoShadowAnchor()).append(";\n");
		buffer.append("return icon;\n");
		buffer.append("})()\n");
		return buffer.toString();
	}
}