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
package org.wicketstuff.openlayers.api;

import java.io.Serializable;

/**
 * 
 */
public class GIcon implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1714038753187423501L;

	private String image = "http://www.google.com/mapfiles/marker.png";
	private String shadow = "http://www.google.com/mapfiles/shadow50.png";
	private GSize iconSize = null;
	private GSize shadowSize = null;
	private GPoint iconAnchor = null;
	private GPoint infoWindowAnchor = null;
	private GPoint infoShadowAnchor = null;

	public GIcon()
	{
	}

	public GIcon(String image)
	{
		this.image = image;
	}

	public String getId()
	{
		return "icon" + String.valueOf(System.identityHashCode(this));
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getImage()
	{
		return image;
	}

	public void setShadow(String shadow)
	{
		this.shadow = shadow;
	}

	public String getShadow()
	{
		return shadow;
	}

	public void setIconSize(GSize size)
	{
		this.iconSize = size;
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
		buffer.append("icon.image = \"").append(image).append("\";\n");
		buffer.append("icon.shadow = \"").append(shadow).append("\";\n");

		if (iconSize != null)
		{
			buffer.append("icon.iconSize = ").append(iconSize.getJSconstructor()).append(";\n");
		}

		if (shadowSize != null)
		{
			buffer.append("icon.shadowSize = ").append(shadowSize.getJSconstructor()).append(";\n");
		}

		if (iconAnchor != null)
		{
			buffer.append("icon.iconAnchor = ").append(iconAnchor.getJSconstructor()).append(";\n");
		}

		if (infoWindowAnchor != null)
		{
			buffer.append("icon.infoWindowAnchor = ").append(infoWindowAnchor.getJSconstructor()).append(";\n");
		}

		if (infoShadowAnchor != null)
		{
			buffer.append("icon.infoShadowAnchor = ").append(infoShadowAnchor.getJSconstructor()).append(";\n");
		}

		buffer.append("return icon;\n");
		buffer.append("})()\n");
		return buffer.toString();
	}
}