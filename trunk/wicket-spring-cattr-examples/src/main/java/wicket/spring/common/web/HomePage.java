/*
 * $Id: HomePage.java 458387 2005-12-28 16:45:07Z ivaynberg $
 * $Revision: 458387 $
 * $Date: 2005-12-28 08:45:07 -0800 (Wed, 28 Dec 2005) $
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
package wicket.spring.common.web;

import wicket.markup.html.link.Link;
import wicket.spring.cattr.web.CommonsAttributePage;

/**
 * Home Page
 *
 * @author Igor Vaynberg (ivaynberg), Karthik Gurumurthy
 *
 */
public class HomePage extends BasePage {
	public HomePage() {

		add(new Link("commons-attr-link") {

			public void onClick() {
				setResponsePage(new CommonsAttributePage());
			}

		});


	}
}
