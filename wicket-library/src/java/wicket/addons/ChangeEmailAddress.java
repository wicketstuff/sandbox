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

import wicket.addons.dao.User;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class ChangeEmailAddress extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    public ChangeEmailAddress()
    {
        super(null, "Wicket-Addons: Change email address");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
        
        add(new ChangeEmailAddressForm("form", feedback));
    }

    public final class ChangeEmailAddressForm extends Form
    {
        private String newEmail;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public ChangeEmailAddressForm(final String componentName, final FeedbackPanel feedback)
        {
            super(componentName, feedback);
            
            add(new TextField("email", new PropertyModel(this, "newEmail")));
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final User user = (User)getAddonDao().load(User.class, new Integer(getAddonSession().getUserId()));
            if (user == null)
            {
				// Try the component based localizer first. If not found try the
				// application localizer. Else use the default
				final String errmsg = getLocalizer().getString("userDeleted", this,
						"Hmmm, user does not exist");

				error(errmsg);
            }
            else
            {
                // update password
                user.setEmail(newEmail);
                getAddonDao().saveOrUpdate(user);
                
                setResponsePage(newPage(getApplication().getPages().getHomePage()));
            }
        }
        
		/**
		 * @return Returns the newEmail
		 */
		public String getNewEmail()
		{
			return newEmail;
		}
		
		/**
		 * @param newEmail
		 */
		public void setNewEmail(final String newEmail)
		{
			this.newEmail = newEmail;
		}
    }
}
