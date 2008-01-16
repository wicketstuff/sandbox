/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.activewidgets.examples.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Classes;


/**
 * Base class for all example pages. 
 * This file based on the main wicket example page.
 * 
 * @author Jonathan Locke
 * @author Oleg Taranenko
 */
public class WicketExamplePage extends WebPage {
	

	/** Serialization */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public WicketExamplePage() {
		this(null);
	}

	/**
	 * Construct.
	 * 
	 * @param model
	 */
	public WicketExamplePage(IModel model) {
		super(model);
		// final String packageName = getClass().getPackage().getName();
		// add(new WicketExampleHeader("mainNavigation",
		// Strings.afterLast(packageName, '.'), this));

		String className = Classes.simpleName(getClass());
		add(new WicketExampleHeader("mainNavigation", className, this));

		explain();
	}

	/**
	 * Override base method to provide an explanation
	 */
	protected void explain() {
	}
}
