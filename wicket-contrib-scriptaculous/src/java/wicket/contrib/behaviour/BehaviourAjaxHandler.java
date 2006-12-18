/*
 * $Id: ScriptaculousAjaxHandler.java 759 2006-06-05 14:07:46 -0700 (Mon, 05 Jun
 * 2006) eelco12 $ $Revision: 955 $ $Date: 2006-06-05 14:07:46 -0700 (Mon, 05
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
package wicket.contrib.behaviour;

import wicket.Component;
import wicket.ResourceReference;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * Handles event requests using 'behaviour'.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 *
 * @see <a href="http://bennolan.com/behaviour/">behaviour</a>
 */
public abstract class BehaviourAjaxHandler extends AbstractAjaxBehavior
{

	public static BehaviourAjaxHandler newJavascriptBindingHandler()
	{
		return new BehaviourAjaxHandler()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see wicket.behavior.IBehavior#onException(Component,
			 *      RuntimeException)
			 */
			public void onException(Component component, RuntimeException exception)
			{
			}

			/**
			 * @see wicket.behavior.IBehaviorListener#onRequest()
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

		response.renderJavascriptReference(new ResourceReference(BehaviourAjaxHandler.class, "behaviour.js"));
	}
}
