package org.wicketstuff.openlayers.api;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.wicketstuff.openlayers.OpenLayersMap;

/**
 * Represents an Google Maps API's <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GInfoWindow">GInfoWindow</a>.
 */
public class GInfoWindow extends WebMarkupContainer
{
	private GInfoWindowTab[] tabs;

	private LonLat latLng;

	private Marker marker;

	private RepeatingView content = new RepeatingView("content");

	public GInfoWindow()
	{
		super("infoWindow");

		setOutputMarkupId(true);
		add(content);
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();

		if (Boolean.parseBoolean(request.getParameter("hidden")))
		{
			// Attention: don't use close() as this might result in an
			// endless AJAX request loop
			setTabs(new GInfoWindowTab[0]);
			marker = null;
			latLng = null;
		}
	}

	public final String getJSinit()
	{
		if (latLng != null)
		{
			return getJSopen(latLng, tabs);
		}

		if (marker != null)
		{
			return getJSopen(marker, tabs);
		}

		return "";
	}

	private void setTabs(GInfoWindowTab[] tabs)
	{
		content.removeAll();

		this.tabs = tabs;
		for (GInfoWindowTab tab : tabs)
		{
			content.add(tab.getContent());
		}
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GInfoWindow open(LonLat latLng, Component content)
	{
		return open(latLng, new GInfoWindowTab(content));
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GInfoWindow open(Marker marker, Component content)
	{
		return open(marker, new GInfoWindowTab(content));
	}

	public GInfoWindow open(LonLat latLng, GInfoWindowTab... tabs)
	{
		setTabs(tabs);

		this.latLng = latLng;
		this.marker = null;

		if (AjaxRequestTarget.get() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSopen(latLng, tabs));
			AjaxRequestTarget.get().addComponent(this);
		}

		return this;
	}

	public GInfoWindow open(Marker marker, GInfoWindowTab... tabs)
	{
		setTabs(tabs);

		this.latLng = null;
		this.marker = marker;

		if (AjaxRequestTarget.get() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSopen(marker, tabs));
			AjaxRequestTarget.get().addComponent(this);
		}

		return this;
	}

	public boolean isOpen()
	{
		return (latLng != null || marker != null);
	}

	public void close()
	{
		setTabs(new GInfoWindowTab[0]);

		marker = null;
		latLng = null;

		if (AjaxRequestTarget.get() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSclose());
			AjaxRequestTarget.get().addComponent(this);
		}
	}

	private String getJSopen(LonLat latLng, GInfoWindowTab[] tabs)
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("openInfoWindowTabs(");
		buffer.append(latLng.getJSconstructor());
		buffer.append(",[");

		boolean first = true;
		for (GInfoWindowTab tab : tabs)
		{
			if (!first)
			{
				buffer.append(",");
			}
			buffer.append(tab.getJSconstructor());
			first = false;
		}

		buffer.append("])");

		return getGMap2().getJSinvoke(buffer.toString());
	}

	private String getJSopen(Marker marker, GInfoWindowTab[] tabs)
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("openMarkerInfoWindowTabs('");
		buffer.append(marker.getId());
		buffer.append("',[");

		boolean first = true;
		for (GInfoWindowTab tab : tabs)
		{
			if (!first)
			{
				buffer.append(",");
			}
			buffer.append(tab.getJSconstructor());
			first = false;
		}

		buffer.append("])");

		return getGMap2().getJSinvoke(buffer.toString());
	}

	private String getJSclose()
	{
		return getGMap2().getJSinvoke("closeInfoWindow()");
	}

	private OpenLayersMap getGMap2()
	{
		return (OpenLayersMap)findParent(OpenLayersMap.class);
	}

}