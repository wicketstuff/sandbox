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

import java.util.Map;

import wicket.RequestCycle;
import wicket.addons.BaseHtmlPage;
import wicket.addons.dao.User;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class AddOrModifyUser extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     */
    public AddOrModifyUser()
    {
        this(-1);
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public AddOrModifyUser(final int userId)
    {
        super(null, "Wicket-Addons: Category Request Form");
        
        final User user;
        if (userId <= 0)
        {
            user = new User();
        }
        else
        {
            user = (User)getAddonDao().load(User.class, new Integer(userId));
        }
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new AddUserForm("form", feedback, user));
    }

    /**
     * 
     */
    public final class AddUserForm extends Form
    {
        final private User user;
        
        private String confirmPassword;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public AddUserForm(final String componentName, final FeedbackPanel feedback, final User user)
        {
            super(componentName, feedback);
            
            this.user = user;
            
            add(new TextField("loginname", new PropertyModel(user, "nickname")));
            add(new TextField("firstname", new PropertyModel(user, "firstname")));
            add(new TextField("lastname", new PropertyModel(user, "lastname")));
            add(new TextField("email", new PropertyModel(user, "email")));
            add(new PasswordTextField("password", new PropertyModel(user, "password")));
            add(new PasswordTextField("confirmPassword", new Model(confirmPassword)));
            
            WebMarkupContainer button = new WebMarkupContainer("delete");
            add(button);
            
            if (user.getId() == 0)
            {
                button.setVisible(false);
            }

        }
        
        public void setConfirmPassword(final String password)
        {
            this.confirmPassword = password;
        }
        
        public String getConfirmPassword()
        {
            return confirmPassword;
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            final Map param = cycle.getRequest().getParameterMap();

            // TODO password check etc. 
            
            if (cycle.getRequest().getParameter("save") != null)
            {
                getAddonDao().saveOrUpdate(user);
            }
            else if (cycle.getRequest().getParameter("delete") != null)
            {
                getAddonDao().delete(user);
            }
            
            cycle.setResponsePage(new Categories(null));
        }
    }
    
}
