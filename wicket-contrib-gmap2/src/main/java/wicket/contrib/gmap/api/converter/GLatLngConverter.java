/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap.api.converter;

import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.wicket.util.convert.IConverter;

import wicket.contrib.gmap.api.GLatLng;

public class GLatLngConverter implements IConverter
{
	private static final long serialVersionUID = 1L;

	/**
	 * The singleton instance for a integer converter
	 */
	public static final GLatLngConverter INSTANCE = new GLatLngConverter();

	private GLatLngConverter() {
		// Prevent construction - use INSTANCE.
	}
	
	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String, java.util.Locale)
	 */
	public GLatLng convertToObject(String value, Locale locale)
	{
		// Expects values like: "(37.442061079130895, -122.13905453681946)"
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

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToString(java.lang.Object, java.util.Locale)
	 */
	public String convertToString(Object value, Locale locale)
	{
		return null;
	}
}
