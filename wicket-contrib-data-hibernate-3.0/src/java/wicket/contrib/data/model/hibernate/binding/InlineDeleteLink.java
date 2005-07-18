package wicket.contrib.data.model.hibernate.binding;

import org.hibernate.Session;

import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao.IHibernateCallback;
import wicket.markup.html.StaticResource;
import wicket.markup.html.image.Image;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;

/**
 * A link that deletes the row's model object from the database.
 * 
 * @author Phil Kulak
 */
public class InlineDeleteLink extends Link
{
	/** the image representing this link */
	public static final StaticResource DELETE = StaticResource.get(InlineEditLink.class
			.getPackage(), "delete.gif");

	/**
	 * @param id
	 *            the id of this link
	 */
	public InlineDeleteLink(String id)
	{
		super(id);
		Image image = new Image("image");
		image.setImageResource(DELETE);
		add(image);
	}

	/**
	 * Deletes the row's model object from the database.
	 */
	public void onClick()
	{
		GridView.deleteRowModel(this);
	}
}
