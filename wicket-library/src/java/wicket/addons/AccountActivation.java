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

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class AccountActivation extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public AccountActivation(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Account Activation Page");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        // Add edit book form to page
        add(new AccountActivationForm("form"));
    }
    
    public final class AccountActivationForm extends Form
    {
        private String activationCode;

        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public AccountActivationForm(final String componentName)
        {
            super(componentName);

            add(new TextField("code", new PropertyModel(this, "activationCode")));
        }

        public void setActivationCode(final String code)
        {
            this.activationCode = code;
        }
        
        public String getActivationCode()
        {
            return this.activationCode;
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            if (activationCode == null)
            {
                String xxx = "1234";
            }
            
            final RequestCycle cycle = getRequestCycle();
            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
