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
package wicket.contrib.gmap.api;

import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Root class for any class that represents a GMap JavaScript class or object.
 */
public abstract class GMapBehavior extends AbstractAjaxBehavior implements
		Identifiable {

	/**
	 * @see wicket.contrib.gmap.api.Identifiable#getJSIdentifier()
	 */
	public String getJSIdentifier() {
		return "" + System.identityHashCode(this);

	}

	/**
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public abstract void renderHead(IHeaderResponse response);
}
