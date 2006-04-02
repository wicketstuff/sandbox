package wicket.contrib.data.model.bind;

import wicket.markup.html.PackageResource;
import wicket.markup.html.image.Image;
import wicket.markup.html.link.Link;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.collections.MicroMap;
import wicket.util.string.Strings;

/**
 * A link that deletes the row's model object from the database.
 * 
 * @author Phil Kulak
 */
public class InlineDeleteLink extends Link
{
	/** the image representing this link */
	public static final PackageResource DELETE = PackageResource.get(InlineEditLink.class, "delete.gif");

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
	
	protected String getOnClickScript(String url)
	{
		return "return confirm('" + getDeleteString() + "');";
	}
	
	protected IModel getResourceModel()
	{
		return new Model(new MicroMap("model", GridView.getRowModel(this).toString()));
	}
	
	protected String getDeleteString()
	{
		String property = GridView.getResourceId(this) + ".deleteItem";
		
		String message = getApplication().getResourceSettings().getLocalizer().getString(
			property, getPage(), getResourceModel(), null, null,
			"Are you sure you want to delete the row?" );
		
		return Strings.replaceAll(message, "'", "\\'").toString();
	}
}
