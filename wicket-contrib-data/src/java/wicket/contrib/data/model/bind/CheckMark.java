package wicket.contrib.data.model.bind;

import wicket.Resource;
import wicket.WicketRuntimeException;
import wicket.markup.html.PackageResource;
import wicket.markup.html.image.Image;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.string.StringValueConversionException;
import wicket.util.string.Strings;

/**
 * A read-only check box.
 * 
 * @author Phil Kulak
 */
public class CheckMark extends Image
{
	/** the image of a check mark */
	public static final PackageResource IMAGE = PackageResource.get(CheckMark.class, "checkMark.gif");

	public CheckMark(String id, IModel model)
	{
		super(id, model);
	}
	
	public CheckMark(String id, Boolean checked)
	{
		super(id, new Model(checked));
	}

	/**
	 * A check mark is visible under the same circumstances that a check box is
	 * checked.
	 * 
	 * @return true if the check mark is visible
	 */
	public boolean isVisible()
	{
		String value = this.getModelObjectAsString();
		try
		{
			return Strings.isTrue(value);
		}
		catch (StringValueConversionException e)
		{
			throw new WicketRuntimeException("Invalid boolean value \"" + value + "\"", e);
		}
	}

	protected Resource getImageResource()
	{
		return IMAGE;
	}
}
