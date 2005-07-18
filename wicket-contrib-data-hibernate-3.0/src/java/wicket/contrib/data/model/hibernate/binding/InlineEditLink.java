package wicket.contrib.data.model.hibernate.binding;

import wicket.AttributeModifier;
import wicket.Resource;
import wicket.markup.html.StaticResource;
import wicket.markup.html.image.Image;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;

/**
 * A link that toggles the edit status of its list item.
 * 
 * @author Phil Kulak
 */
public class InlineEditLink extends Link
{
	/** an image for a cancel button */
	public static final StaticResource CANCEL = StaticResource.get(InlineEditLink.class
			.getPackage(), "cancel.gif");

	/** an image for an edit button */
	public static final StaticResource EDIT = StaticResource.get(InlineEditLink.class
			.getPackage(), "edit.gif");

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

		// TODO these strings should be externalized
		image.add(new AttributeModifier("alt", true, new AttributeModel()
		{
			protected String getAttributeValue()
			{
				if (GridView.isEdit(InlineEditLink.this))
				{
					return "Cancel";
				}
				return "Edit";
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
}
