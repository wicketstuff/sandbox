package wicket.contrib.data.model.hibernate.binding;

import wicket.Resource;
import wicket.WicketRuntimeException;
import wicket.markup.html.StaticResource;
import wicket.markup.html.image.Image;
import wicket.model.IModel;
import wicket.util.string.StringValueConversionException;
import wicket.util.string.Strings;

public class CheckMark extends Image {
	public static final StaticResource CHECK_MARK =
		StaticResource.get(CheckMark.class.getPackage(), "checkMark.gif");
	
	public CheckMark(String id, IModel model) {
		super(id, model);
	}

	public boolean isVisible() {
		String value = this.getModelObjectAsString();
		try	{
			return Strings.isTrue(value);
		} catch (StringValueConversionException e) {
			throw new WicketRuntimeException("Invalid boolean value \""
				+ value + "\"", e);
		}
	}

	protected Resource getImageResource() {
		return CHECK_MARK;
	}
}
