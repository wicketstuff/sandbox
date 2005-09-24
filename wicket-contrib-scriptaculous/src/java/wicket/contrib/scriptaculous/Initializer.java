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
package wicket.contrib.scriptaculous;

import wicket.Application;
import wicket.IInitializer;
import wicket.util.resource.IResourceStream;

/**
 * Initializer for components in the Wicket-Scriptaculous integration library.
 */
public class Initializer implements IInitializer {
	/**
	 * @see wicket.IInitializer#init(wicket.Application)
	 */
	public void init(Application application) {
		// implement the ajax handlers using dummy implementations
		new ScriptaculousAjaxHandler() {

			protected IResourceStream getResponse() {
				return null;
			}

		}.init(application);
	}
}
