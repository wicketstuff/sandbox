/*
 * $Id: ScriptaculousAjaxBehavior.java 2017 2007-04-29 14:59:34 +0000 (Sun, 29 Apr 2007) wireframe6464 $
 * $Revision: 2017 $
 * $Date: 2007-04-29 14:59:34 +0000 (Sun, 29 Apr 2007) $
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
package org.wicketstuff.scriptaculous;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Handles event requests using 'script.aculo.us'.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 * 
 * @see <a href="http://script.aculo.us/">script.aculo.us</a>
 */
public abstract class ScriptaculousAjaxBehavior extends AbstractAjaxBehavior {

	public static ScriptaculousAjaxBehavior newJavascriptBindingBehavior() {
		return new ScriptaculousAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			protected IResourceStream getResponse() {
				return null;
			}
		};
	}

	/**
	 * @see wicket.behavior.IBehaviorListener#onRequest()
	 */
	public void onRequest() {
		IResourceStream response = getResponse();
		if (response != null) {
			boolean isPageVersioned = true;
			try {
				isPageVersioned = getComponent().getPage().isVersioned();
				getComponent().getPage().setVersioned(false);

				ScriptaculousRequestTarget target = new ScriptaculousRequestTarget(
						response);
				RequestCycle.get().setRequestTarget(target);
			} finally {
				getComponent().getPage().setVersioned(isPageVersioned);
			}
		}
	}

	/**
	 * TODO: what is this for?
	 * @return
	 */
	protected abstract IResourceStream getResponse();

	public final void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(
				ScriptaculousAjaxBehavior.class, "prototype.js"));
		response.renderJavascriptReference(new ResourceReference(
				ScriptaculousAjaxBehavior.class, "scriptaculous.js"));
	}
}
