package wicket.contrib.data.model.bind;

import org.apache.wicket.Resource;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.util.string.Strings;

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
