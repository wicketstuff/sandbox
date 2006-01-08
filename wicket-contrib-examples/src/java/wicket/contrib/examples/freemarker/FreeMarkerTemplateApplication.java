/*
 * $Id: FreeMarkerTemplateApplication.java,v 1.1 2005/09/01 13:44:28 jklingstedt
 * Exp $ $Revision$ $Date$
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
package wicket.contrib.examples.freemarker;

import java.util.ArrayList;
import java.util.List;

import wicket.protocol.http.WebApplication;
import wicket.util.time.Duration;

/**
 * Application class for FreeMarker template example.
 * 
 * @author Jonas Klingstedt
 */
public class FreeMarkerTemplateApplication extends WebApplication
{
	/** Dummy people database */
	private static List people = new ArrayList();

	static
	{
		people.add(new Person("Joe", "Down"));
		people.add(new Person("Fritz", "Frizel"));
		people.add(new Person("Flip", "Vlieger"));
		people.add(new Person("George", "Forrest"));
		people.add(new Person("Sue", "Hazel"));
		people.add(new Person("Bush", "Gump"));
	}

	/**
	 * Gets the dummy people database.
	 * 
	 * @return the dummy people database
	 */
	public static List getPeople()
	{
		return people;
	}

	/**
	 * Constructor.
	 */
	public FreeMarkerTemplateApplication()
	{
		getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
	}

	/**
	 * @return class
	 */
	public Class getHomePage()
	{
		return TemplatePage.class;
	}
}
