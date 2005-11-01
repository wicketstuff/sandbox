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

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.BaseHtmlPage;
import wicket.addons.CategoryRequestStored;
import wicket.markup.html.form.Form;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Juergen Donnerstag
 */
public final class WorkItemList extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public WorkItemList(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Category Request Form");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new CategoryRequestForm("form"));
    }

    public final class CategoryRequestForm extends Form
    {
        /**
         * Constructor
         * @param componentName Name of form
         */
        public CategoryRequestForm(final String componentName)
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
            cycle.setResponsePage(new CategoryRequestStored());
        }
    }
    
}
