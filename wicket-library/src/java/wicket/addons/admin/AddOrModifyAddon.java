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

import java.util.List;
import java.util.Map;

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.BaseHtmlPage;
import wicket.addons.dao.Addon;
import wicket.addons.dao.Category;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class AddOrModifyAddon extends BaseHtmlPage /* AuthenticateHtmlPage */
{
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
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);
        
        add(new AddonSubmitForm("form", feedback, addon));
    }

    public final class AddonSubmitForm extends Form
    {
        final private Addon addon;
        private String otherLicense;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public AddonSubmitForm(final String componentName, final FeedbackPanel feedback, final Addon addon)
        {
            super(componentName, feedback);
            this.addon = (Addon)getAddonDao().load(addon);
            
            add(new TextField("personname", new Model("")));
            add(new TextField("email", new Model("")));
            add(new TextField("name", new PropertyModel(addon, "name")));
            add(new TextArea("description", new PropertyModel(addon, "description")));
            add(new TextField("homepage", new PropertyModel(addon, "homepage")));
            add(new TextField("downloadUrl", new PropertyModel(addon, "downloadUrl")));
            add(new TextField("version", new PropertyModel(addon, "version")));
            add(new TextField("wicketVersion", new PropertyModel(addon, "wicketVersion")));
            add(new TextField("otherLicense", new PropertyModel(this, "otherLicense")));
            
            final List licenseList = getAddonDao().getLicenseNames();
            add(new DropDownChoice("license", new PropertyModel(addon, "license"), licenseList));
        }

        public void setOtherLicense(final String license)
        {
            this.otherLicense = license;
        }
        
        public String getOtherLicense()
        {
            return otherLicense;
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            final Map params = cycle.getRequest().getParameterMap();

            if ("accept".equals(params.get("accept")))
            {
                if (otherLicense != null)
                {
                    addon.setLicense(otherLicense);
                }
         
                addon.setEnabled(1);
                addon.setCategory((Category)getAddonDao().load(Category.class, new Integer(1)));
                getAddonDao().saveOrUpdate(addon);
            }

            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
