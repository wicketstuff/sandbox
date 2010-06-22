package org.wicketstuff.html5.markup.html.form;

import java.io.Serializable;
import java.net.URL;

public class Pojo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Double range;

	private URL url;
	
	public Pojo() {
	}

	public Double getRange() {
		return range;
	}

	public void setRange(Double range) {
		this.range = range;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
}