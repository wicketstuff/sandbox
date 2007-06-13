package wicket.contrib.gmap.api.converter;

import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.wicket.util.convert.IConverter;

import wicket.contrib.gmap.api.GLatLng;

public class GLatLngConverter implements IConverter
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The singleton instance for a integer converter
	 */
	public static final IConverter INSTANCE = new GLatLngConverter();


	// (37.442061079130895, -122.13905453681946)
	public Object convertToObject(String value, Locale locale)
	{
		if (value == null || value.equals("undefined"))
		{
			return null;
		}
		StringTokenizer tokenizer;
		try
		{
			tokenizer = new StringTokenizer(value, "(, )");
		}
		catch (NullPointerException e)
		{
			return null;
		}
		if (tokenizer.countTokens() != 2)
		{
			return null;
		}
		float lat = Float.valueOf(tokenizer.nextToken());
		float lng = Float.valueOf(tokenizer.nextToken());
		return new GLatLng(lat, lng);
	}

	public String convertToString(Object value, Locale locale)
	{
		return null;
	}
}
