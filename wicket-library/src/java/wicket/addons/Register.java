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
import wicket.addons.dao.User;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class Register extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public Register(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Register page");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        // Add edit book form to page
        final User user = new User();
        add(new RegisterUserForm("register", user, feedback));
    }

    public final class RegisterUserForm extends Form
    {
        private User user;
        private String passwordConfirm;

        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public RegisterUserForm(final String componentName, final User user,
            final FeedbackPanel feedback)
        {
            super(componentName, feedback);

            // Set model
            this.user = user;

            add(new TextField("loginname", new PropertyModel(user, "nickname")));
            add(new TextField("email", new PropertyModel(user, "email")));
            add(new PasswordTextField("password", new PropertyModel(user, "password")));
            add(new PasswordTextField("passwordConfirm", new PropertyModel(this, "passwordConfirm")));
        }

        public void setPasswordConfirm(final String passwordConfirm)
        {
            this.passwordConfirm = passwordConfirm;
        }

        public String getPasswordConfirm()
        {
            return this.passwordConfirm;
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            if (((passwordConfirm == null) && (user.getPassword() == null)) 
                || ((passwordConfirm != null) && (user.getPassword() != null) && passwordConfirm.equals(user.getPassword())))
            {
                String xxx = "1234";
            }
            
            final RequestCycle cycle = getRequestCycle();
            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
