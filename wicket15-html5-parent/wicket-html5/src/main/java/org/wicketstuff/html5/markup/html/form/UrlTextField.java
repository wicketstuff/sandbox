package org.wicketstuff.html5.markup.html.form;

import java.net.URISyntaxException;
import java.net.URL;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

public class UrlTextField extends AbstractHtml5TextComponent<URL> {

	private static final long serialVersionUID = 1L;

	private URL url;
	
	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public UrlTextField(final String id)
	{
		this(id, null, null);
	}

	public UrlTextField(final String id, String datalistId)
	{
		this(id, null, datalistId);
	}
	
	/**
	 * @param id
	 * @param model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public UrlTextField(final String id, final IModel<URL> model)
	{
		super(id, model, URL.class);
	}

	public UrlTextField(final String id, final IModel<URL> model, String datalistId)
	{
		super(id, model, URL.class, datalistId);
	}

	/**
	 * Processes the component tag.
	 * 
	 * @param tag
	 *            Tag to modify
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
	
		if (url != null) {
			try {
				tag.put("value", url.toURI().toString());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see org.apache.wicket.markup.html.form.TextField#getInputType()
	 */
	@Override
	protected String getInputType()
	{
		return "url";
	}
	
	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param step the step to set
	 */
	public UrlTextField setUrl(final URL url) {
		this.url = url;
		return this;
	}
}
