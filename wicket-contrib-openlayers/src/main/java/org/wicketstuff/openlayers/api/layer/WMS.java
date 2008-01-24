package org.wicketstuff.openlayers.api.layer;

import org.wicketstuff.openlayers.js.Constructor;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class WMS extends Layer {

	private String name;
	private String url;
	private String[] layers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getLayers() {
		return layers;
	}

	public void setLayers(String[] layers) {
		this.layers = layers;
	}

	public WMS(String name, String url, String[] layers) {
		super();
		this.name = name;
		this.url = url;
		this.layers = layers;
	}

	public String getJSconstructor() {
		String actualLayers = "";
		boolean first = true;
		for (int i = 0; i < layers.length; i++) {
			if (first) {
				first = false;
			} else {
				actualLayers += ",";
			}
			actualLayers += layers[i];
		}

		return new Constructor("OpenLayers.Layer.WMS").add("'" + name + "'")
				.add("'" + url+ "'").add("{layers:'" + actualLayers + "'}")
				.toJS();
	}

}
