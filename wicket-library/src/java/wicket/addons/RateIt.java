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
package wicket.addons;

import java.util.Map;

import wicket.RequestCycle;
import wicket.addons.hibernate.Addon;
import wicket.addons.hibernate.Rating;
import wicket.addons.hibernate.User;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Juergen Donnerstag
 */
public final class RateIt extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public RateIt(final int addonId)
    {
        super(null, "Wicket-Addons: Rate an addon");
   
        final Addon addon = (Addon)getAddonDao().load(Addon.class, new Integer(addonId));
        add(new Label("name", addon.getName()));
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        
        add(new RateItForm("form", feedback, addon));
    }

    public final class RateItForm extends Form
    {
        private final Addon addon;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public RateItForm(final String componentName, final FeedbackPanel feedback, final Addon addon)
        {
            super(componentName, feedback);
            
            this.addon = addon;
            add(new Label("name", addon.getName()));

        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final Rating rating = new Rating();
            
            final RequestCycle cycle = getRequestCycle();
            final Map params = cycle.getRequest().getParameterMap();
            
            rating.setComment((String)params.get("comment"));
            rating.setAddon(addon);
            rating.setUser((User)getUserDao().getUsers().get(0));
            rating.setRating(Integer.valueOf((String)params.get("rating")).intValue());
            
            getAddonDao().saveOrUpdate(rating);
            
            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
