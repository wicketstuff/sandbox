/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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
package wicket.contrib.gmap.behaviour;

import java.util.StringTokenizer;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;

import wicket.contrib.gmap.api.GLatLng;

public abstract class AbstractGMapBehaviour extends AbstractDefaultAjaxBehavior
{
	protected final GLatLng convertToGLatLng(String value)
	{
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
}
