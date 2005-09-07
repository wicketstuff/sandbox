package wicket.contrib.jasperreports;

import wicket.IResourceListener;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebComponent;

/**
 * Reference to the actual report.
 */
public final class PDFReport extends WebComponent  implements IResourceListener
{
	/** the report resource. */
	private final PDFResource resource;

	/**
	 * Construcxt.
	 * @param id component id
	 * @param resource the resource
	 */
	public PDFReport(String id, PDFResource resource)
	{
		super(id);
		this.resource = resource;
	}

	/**
	 * @see wicket.Component#onComponentTag(wicket.markup.ComponentTag)
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		String url = urlFor(IResourceListener.class);
		tag.put("src", getResponse().encodeURL(url));
		super.onComponentTag(tag);
	}

	/**
	 * @see wicket.IResourceListener#onResourceRequested()
	 */
	public void onResourceRequested()
	{
		resource.onResourceRequested();
	}
}