/*
 * $Id$ $Revision$
 * $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.addons;

import wicket.PageParameters;
import wicket.addons.db.Addon;
import wicket.addons.utils.AddOrModifyAddonPanel;

/**
 * @author Juergen Donnerstag
 */
public final class AddonSubmit extends BaseHtmlPage /* AuthenticateHtmlPage */
{
	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public AddonSubmit(final PageParameters parameters)
	{
		super(parameters, "Wicket-Addons: Add-on Submit Form");

		add(new AddOrModifyAddonPanel("addonPanel", null)
		{
			public void onSubmit(Addon addon)
			{
				addon.setEnable(false);
				getAddonService().save(addon);

				setResponsePage(newPage(getApplication().getHomePage()));
			}
		});
	}
}
