package wicket.contrib.gmap.api.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import wicket.contrib.gmap.api.GMarker;

public class GMarkerConverter implements IConverter
{

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The singleton instance for a marker converter
	 */
	public static final IConverter INSTANCE = new GMarkerConverter();


	/**
	 * @see wicket.util.convert.IConverter#convertToObject(java.lang.String, java.util.Locale)
	 */
	public Object convertToObject(String value, Locale locale)
	{
		if (value == null || value.equals("null")){
			return null;
		} else {
			//TODO Do something
			return new GMarker(null);
		}
	}

	public String convertToString(Object value, Locale locale)
	{
		return null;
	}

}
