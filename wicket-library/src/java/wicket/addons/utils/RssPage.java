/*
 * $Id$ $Revision$
 * $Date$
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
package wicket.addons.utils;

import wicket.Page;
import wicket.markup.html.WebPage;
import wicket.model.IModel;

/**
 * Base class for HTML pages. This subclass of Page simply returns HTML when
 * asked for its markup type. It also has a method which subclasses can use to
 * retrieve a bookmarkable link to the application's home page.
 * <p>
 * Pages can be constructed with any constructor when they are being used in a
 * Wicket session, but if you wish to link to a Page using a URL that is
 * bookmarkable (doesn't have session information encoded into it), you need to
 * implement your Page with a constructor that accepts a single PageParameters
 * argument.
 * 
 * @author Jonathan Locke
 */
public class RssPage extends WebPage
{
	/**
	 * Constructor.
	 */
	protected RssPage()
	{
		super();
	}

	/**
	 * @see Page#Page(IModel)
	 */
	protected RssPage(final IModel model)
	{
		super(model);
	}

	/**
	 * Gets the markup type for a WebPage, which is always "html" (and thus the
	 * method is final here). Support for pages in another markup language, such
	 * as VXML, would require the creation of a different Page subclass in an
	 * appropriate package under wicket.markup. To support VXML (voice markup),
	 * one might create the package wicket.markup.vxml and a subclass of Page
	 * called VoicePage.
	 * <p>
	 * Note: The markup type must be equal to the extension of the markup file.
	 * In the case of WebPages, it must always be "html".
	 * 
	 * @return Markup type for HTML
	 */
	protected final String getMarkupType()
	{
		return "rss";
	}
}