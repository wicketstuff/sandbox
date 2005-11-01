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
import wicket.addons.ServiceLocator;
import wicket.addons.db.Person;
import wicket.addons.db.User;
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
        this(null);
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public AddOrModifyUser(User user)
    {
        super(null, "Wicket-Addons: Category Request Form");
        
        if (user == null)
        {
            user = User.Factory.newInstance();
            user.setPerson(Person.Factory.newInstance());
        }
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new AddUserForm("form", user));
    }

    /**
     * 
     */
    public final class AddUserForm extends Form
    {
        private User user;
        private String confirmPassword;
        
        /**
         * Constructor
         * @param componentName Name of form
         */
        public AddUserForm(final String componentName, final User user)
        {
            super(componentName);
            
            this.user = user;
            
            add(new TextField("loginname", new PropertyModel(user, "loginName")));
            add(new TextField("firstname", new PropertyModel(user.getPerson(), "firstName")));
            add(new TextField("lastname", new PropertyModel(user.getPerson(), "lastName")));
            add(new TextField("email", new PropertyModel(user.getPerson(), "email")));
            add(new PasswordTextField("password", new PropertyModel(user, "password")));
            add(new PasswordTextField("confirmPassword", new Model(confirmPassword)));
            
            WebMarkupContainer button = new WebMarkupContainer("delete");
            add(button);
            
            if (user.getId() == null)
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
                if (user.getId() == null)
                {
                    ServiceLocator.instance().getUserService().addUser(user);
                }
                else
                {
                    ServiceLocator.instance().getUserService().update(user);
                }
            }
            else if (cycle.getRequest().getParameter("delete") != null)
            {
                ServiceLocator.instance().getUserService().removeUser(user);
            }
            
            cycle.setResponsePage(new Users(null));
        }
    }
    
}
