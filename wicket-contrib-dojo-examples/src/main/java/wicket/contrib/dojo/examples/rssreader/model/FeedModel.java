package wicket.contrib.dojo.examples.rssreader.model;

import java.io.Serializable;

public class FeedModel implements Serializable{
	private String url;
	private String image;
	private String name;
	
	public FeedModel(String name, String url, String image) {
		this.url = url;
		this.image = image;
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
