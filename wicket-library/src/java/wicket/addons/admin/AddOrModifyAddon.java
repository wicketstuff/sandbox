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
package wicket.addons.admin;

import java.util.Map;

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.BaseHtmlPage;
import wicket.addons.hibernate.Addon;
import wicket.addons.hibernate.Category;
import wicket.addons.utils.AddOrModifyAddonPanel;

/**
 * @author Juergen Donnerstag
 */
public final class AddOrModifyAddon extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    public AddOrModifyAddon()
    {
        this((Addon)null);
    }
    
    public AddOrModifyAddon(final PageParameters parameters)
    {
        this((Addon)null);
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public AddOrModifyAddon(Addon addon)
    {
        super(null, "Wicket-Addons: Add-on Submit Form");
        
        if (addon == null)
        {
            addon = new Addon();
        }
       
        add(new AddOrModifyAddonPanel("addonPanel", getAddonDao(), addon.getId())
        {
            public final void onSubmit(final Addon addon)
            {
                final RequestCycle cycle = getRequestCycle();
                final Map params = cycle.getRequest().getParameterMap();

                if ("save".equals(params.get("save")))
                {
                    addon.setEnabled(1);
                    addon.setCategory((Category)getAddonDao().load(Category.class, new Integer(1)));
                    addon.setOwner(getUser());
                    getAddonDao().saveOrUpdate(addon);
                }

                setResponsePage(new Addons(null));
            }
        });
        
        get("sidebarsRight").setVisible(false);
    }
}
