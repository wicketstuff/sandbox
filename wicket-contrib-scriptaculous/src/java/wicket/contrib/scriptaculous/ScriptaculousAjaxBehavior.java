/*
 * $Id: ScriptaculousAjaxHandler.java 759 2006-06-05 14:07:46 -0700 (Mon, 05 Jun
 * 2006) eelco12 $ $Revision$ $Date: 2006-06-05 14:07:46 -0700 (Mon, 05
 * Jun 2006) $
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

import wicket.ResourceReference;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.markup.html.IHeaderResponse;

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
public abstract class ScriptaculousAjaxBehavior extends AbstractAjaxBehavior
{

	/**
	 * generate a stub just to attach javascript to a component.
	 * @return
	 */
	public static ScriptaculousAjaxBehavior newJavascriptBindingBehavior()
	{
		return new ScriptaculousAjaxBehavior()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * do nothing for this stub
			 */
			public void onRequest()
			{
			}
		};
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(ScriptaculousAjaxBehavior.class,
				"prototype.js"));
		response.renderJavascriptReference(new ResourceReference(ScriptaculousAjaxBehavior.class,
				"scriptaculous.js"));
	}
}
