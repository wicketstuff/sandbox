package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.HashMap;

import org.wicketstuff.openlayers.js.Constructor;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class WMS extends Layer implements Serializable {

	private String name;
	private String url;
	/**
	 * can be any of : layers: 'topp:AL_03C5E', styles: '', height: '750',
	 * width: '800', srs: 'EPSG:2400', format: 'image/png', tiled: 'true',
	 * tilesOrigin : "1319519.4432429108,6224522.644438478"
	 * 
	 * 
	 */
	private HashMap<String, String> options = new HashMap<String, String>();

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

	public WMS(String name, String url, HashMap<String, String> options) {
		super();
		this.name = name;
		this.url = url;
		this.options = options;

	}

	/*
	 * 
	 * tiled = new OpenLayers.Layer.WMS( "topp:AL_03C5E - Tiled",
	 * "http://neubauer.se:8080/geoserver/wms", { layers: 'topp:AL_03C5E',
	 * styles: '', height: '750', width: '800', srs: 'EPSG:2400', format:
	 * 'image/png', tiled: 'true', tilesOrigin :
	 * "1319519.4432429108,6224522.644438478" }, {buffer: 0}
	 */

	public String getJSconstructor() {
		String optionlist = "";

		boolean first = true;

		for (String key : options.keySet()) {
			if (first) {
				first = false;
			} else {
				optionlist += ",\n";
			}
			optionlist += key + ": " + options.get(key) ;

		}

		return new Constructor("OpenLayers.Layer.WMS").add("'" + name + "'")
				.add("'" + url + "'").add("{" + optionlist + "}").toJS();
	}

}
