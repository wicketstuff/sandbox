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
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class ChangePassword extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    public ChangePassword()
    {
        super(null, "Wicket-Addons: Change Password");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
        
        add(new ChangePasswordForm("form", feedback));
    }

    public final class ChangePasswordForm extends Form
    {
        private String currentPassword;
        private String newPassword;
        private String newPasswordConfirm;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public ChangePasswordForm(final String componentName, final FeedbackPanel feedback)
        {
            super(componentName, feedback);
            
            add(new PasswordTextField("currentPassword", new PropertyModel(this, "currentPassword")));
            add(new PasswordTextField("newPassword", new PropertyModel(this, "newPassword")));
            add(new PasswordTextField("newPasswordConfirm", new PropertyModel(this, "newPasswordConfirm")));
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            if ((newPassword != null) && (newPassword.equals(newPasswordConfirm)))
            {
                // confirm current password
                final String username = getAddonSession().getUserLogonName();
                final User user = ((AddonApplication)getApplication()).authenticate(username, currentPassword);
                if (user == null)
                {
    				// Try the component based localizer first. If not found try the
    				// application localizer. Else use the default
    				final String errmsg = getLocalizer().getString("currentPasswordMismatch", this,
    						"password mismatch");

    				error(errmsg);
                }
                else
                {
                    // update password
                    getAddonDao().changePassword(getAddonSession().getUserId(), newPassword);
                    
                    setResponsePage(newPage(getApplication().getPages().getHomePage()));
                }
            }
            else
            {
				// Try the component based localizer first. If not found try the
				// application localizer. Else use the default
				final String errmsg = getLocalizer().getString("confirmPasswordMismatch", this,
						"confirm password must match new password");

				error(errmsg);
            }
        }
        
		/**
		 * @return Returns the currentPassword.
		 */
		public String getCurrentPassword()
		{
			return currentPassword;
		}
		
		/**
		 * @param currentPassword The currentPassword to set.
		 */
		public void setCurrentPassword(String currentPassword)
		{
			this.currentPassword = currentPassword;
		}
		
		/**
		 * @return Returns the newPassword.
		 */
		public String getNewPassword()
		{
			return newPassword;
		}
		
		/**
		 * @param newPassword The newPassword to set.
		 */
		public void setNewPassword(String newPassword)
		{
			this.newPassword = newPassword;
		}
		
		/**
		 * @return Returns the newPasswordConfirm.
		 */
		public String getNewPasswordConfirm()
		{
			return newPasswordConfirm;
		}
		
		/**
		 * @param newPasswordConfirm The newPasswordConfirm to set.
		 */
		public void setNewPasswordConfirm(String newPasswordConfirm)
		{
			this.newPasswordConfirm = newPasswordConfirm;
		}
    }
}
