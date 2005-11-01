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

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.markup.html.form.Form;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Juergen Donnerstag
 */
public final class Feedback extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public Feedback(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Register page");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        add(new FeedbackForm("feedback"));
    }

    public final class FeedbackForm extends Form
    {
        /**
         * Constructor
         * @param componentName Name of form
         */
        public FeedbackForm(final String componentName)
        {
            super(componentName);
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            final Map params = cycle.getRequest().getParameterMap();
            final String name = (String)params.get("name");
            final String email = (String)params.get("email");
            final String text = (String)params.get("description");
            
            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
