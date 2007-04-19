package wicket.contrib.data.model.bind;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Resource;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;

/**
 * A link that toggles the edit status of its list item.
 * 
 * @author Phil Kulak
 */
public class InlineEditLink extends Link
{
	/** an image for a cancel button */
	public static final PackageResource CANCEL = PackageResource.get(InlineEditLink.class, "cancel.gif");

	/** an image for an edit button */
	public static final PackageResource EDIT = PackageResource.get(InlineEditLink.class, "edit.gif");

	/**
	 * @param id
	 *            the id of this link
	 */
	public InlineEditLink(String id)
	{
		super(id);

		Image image = new Image("image")
		{
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
			protected String getAttributeValue()
			{
				if (GridView.isEdit(InlineEditLink.this))
				{
					return getCancelAlt();
				}
				return getEditAlt();
			}
		}));

		add(image);
	}

	/**
	 * Toggles the edit status of the list item.
	 */
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
			GridView.getResourceId(this) + ".cancelAlt", getPage(), null, null,
			null, "Cancel");
	}
	
	protected String getEditAlt()
	{
		return getApplication().getResourceSettings().getLocalizer().getString(
			GridView.getResourceId(this) + ".editAlt", getPage(), null, null,
			null, "Edit");
	}
}
