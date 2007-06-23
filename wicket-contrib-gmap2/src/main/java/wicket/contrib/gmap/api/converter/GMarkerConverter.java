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
		return value.toString();
	}

}
