package org.wicketstuff.dojo.markup.html.yahoomap;

import java.io.Serializable;

import org.apache.wicket.Component;

public class DojoMapData implements Serializable {
	
	private double latitude;
	private double longitude;
	private String iconUrl;
	private Component description;
	
	public DojoMapData(double latitude, double longitude, String iconUrl, Component description) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.iconUrl = iconUrl;
		this.description = description;
	}

	public Component getDescription() {
		return description;
	}

	public void setDescription(Component description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	

}
