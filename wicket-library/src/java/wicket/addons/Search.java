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

import wicket.IFeedback;
import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.markup.html.form.Form;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Juergen Donnerstag
 */
public final class Search extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public Search(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Search page");
        
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        add(new SearchForm("form", feedback));
    }

    public final class SearchForm extends Form
    {
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public SearchForm(final String componentName, final IFeedback feedback)
        {
            super(componentName, feedback);
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            
            // This is an example on how to get input from an <input>
            // even without a Wicket TextField. 
            final Map params = cycle.getRequest().getParameterMap();
            final String searchText = (String)params.get("query"); // the "name" of the input field
            if ((searchText != null) && (searchText.trim().length() > 0))
            {
                String xxx = "1234";
            }
            
            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
