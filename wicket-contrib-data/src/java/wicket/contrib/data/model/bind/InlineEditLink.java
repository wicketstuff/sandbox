package wicket.contrib.data.model.bind;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.Resource;
import wicket.markup.html.PackageResource;
import wicket.markup.html.image.Image;
import wicket.markup.html.link.Link;

/**
 * A link that toggles the edit status of its list item.
 * 
 * @author Phil Kulak
 */
public class InlineEditLink extends Link
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** an image for a cancel button */
	public static final PackageResource CANCEL = PackageResource.get(
			InlineEditLink.class, "cancel.gif");

	/** an image for an edit button */
	public static final PackageResource EDIT = PackageResource.get(InlineEditLink.class,
			"edit.gif");

	/**
	 * @param id
	 *            the id of this link
	 */
	public InlineEditLink(MarkupContainer parent, String id)
	{
		super(parent, id);

		Image image = new Image(this, "image")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected Resource getImageResource()
			{
				if (GridView.isEdit(this))
				{
					return CANCEL;
				}
				return EDIT;
			}
		};

		image.add(new AttributeModifier("alt", true, new AttributeModel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected String getAttributeValue()
			{
				if (GridView.isEdit(InlineEditLink.this))
				{
					return getCancelAlt();
				}
				return getEditAlt();
			}
		}));
	}

	/**
	 * Toggles the edit status of the list item.
	 */
	@Override
	public void onClick()
	{
		if (GridView.isEdit(this))
		{
			GridView.removeEdit(this);
		}
		else
		{
			GridView.setEdit(this);
		}
	}

	protected String getCancelAlt()
	{
		return getApplication().getResourceSettings().getLocalizer().getString(
				GridView.getResourceId(this) + ".cancelAlt", getPage(), null, null, null,
				"Cancel");
	}

	protected String getEditAlt()
	{
		return getApplication().getResourceSettings().getLocalizer().getString(
				GridView.getResourceId(this) + ".editAlt", getPage(), null, null, null,
				"Edit");
	}
}
