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

import java.io.Serializable;
import java.util.List;

import wicket.AttributeModifier;
import wicket.Page;
import wicket.PageParameters;
import wicket.addons.AddonApplicationBorderWithoutRightSidebar;
import wicket.addons.BaseHtmlPage;
import wicket.addons.dao.User;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class Users extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private static final String PAGE_TITLE = "Wicket-Addons: Maintaine categories";

    private ListView table;
    
    /**
     * Constructor
     * @param parameters
      */
    public Users(final PageParameters parameters)
    {
        super(parameters, PAGE_TITLE, new AddonApplicationBorderWithoutRightSidebar("border", PAGE_TITLE));
        
        final List users = getUserDao().getUsers();
        
        // Add table of existing comments
        this.table = new ListView("rows", users)
        {
            public void populateItem(final ListItem listItem)
            {
                // alternating row styles
                listItem.add(new AttributeModifier("class",
                        new Model((listItem.getIndex() % 2) == 0 ? "even" : "odd")));
                
                final User value = (User) listItem.getModelObject();

                listItem.add(new MyPageLink("loginname", value, value.getNickname()));
                listItem.add(new MyPageLink("name", value, value.getNameLastNameFirst()));
                listItem.add(new MyPageLink("email", value, value.getEmail()));
                listItem.add(new MyPageLink("lastLogin", value, value.getLastLogin()));
                listItem.add(new MyPageLink("deactivated", value, value.getDeactivated()));
                listItem.add(new MyPageLink("deleted", value, value.getDeleted()));
            }
        };

        add(table);
        
        add(new PageLink("new", AddOrModifyUser.class));
    }
    
    /**
     * @see wicket.Component#onRender(wicket.RequestCycle)
     */
    protected void onBeginRequest()
    {
        final List users = getUserDao().getUsers();
        this.table.setModelObject(users);
    }
    
    private class MyPageLink extends PageLink
    {
        public MyPageLink(final String componentName, final User user, final Serializable value)
        {
            super(componentName + "Link", new IPageLink()
	            {
	                public Page getPage()
	                {
	                    return new AddOrModifyUser(user.getId());
	                }
	
	                public Class getPageIdentity()
	                {
	                    return user.getClass();
	                }
	            });
         
            String label = (value != null ? value.toString() : "&nbsp");
            if (label.length() == 0)
            {
                label ="&nbsp;";
            }
            
            add(new Label(componentName, label));
        }
    }
}
