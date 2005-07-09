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

import java.util.List;

import wicket.addons.hibernate.User;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class ChangeUsername extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    public ChangeUsername()
    {
        super(null, "Wicket-Addons: Change Username");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
        
        add(new ChangeUsernameForm("form", feedback));
    }

    public final class ChangeUsernameForm extends Form
    {
        private String newUsername;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public ChangeUsernameForm(final String componentName, final FeedbackPanel feedback)
        {
            super(componentName, feedback);
            
            add(new TextField("username", new PropertyModel(this, "newUsername")));
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            if ((newUsername != null) && (newUsername.trim().length() > 0))
            {
                // confirm current password
                final String username = getAddonSession().getUserLogonName();
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
                    final List users = getUserDao().loadUserByNickname(newUsername);
                    if (users.size() != 0)
                    {
        				// Try the component based localizer first. If not found try the
        				// application localizer. Else use the default
        				final String errmsg = getLocalizer().getString("usernameAlreadyExists", this,
        						"Username already exists. Please choose another one");

        				error(errmsg);
                    }
                    else
                    {
	                    // update password
                        user.setNickname(newUsername);
	                    getAddonDao().saveOrUpdate(user);
	                    getAddonSession().setUserLogonName(newUsername);
	                    
	                    setResponsePage(newPage(getApplication().getPages().getHomePage()));
                    }
                }
            }
            else
            {
				// Try the component based localizer first. If not found try the
				// application localizer. Else use the default
				final String errmsg = getLocalizer().getString("newUsernameEmpty", this,
						"username must not be empty");

				error(errmsg);
            }
        }
        
		/**
		 * @return Returns the newUsername
		 */
		public String getNewUsername()
		{
			return newUsername;
		}
		
		/**
		 * @param newUsername The new username to set.
		 */
		public void setNewUsername(final String newUsername)
		{
			this.newUsername = newUsername;
		}
    }
}
