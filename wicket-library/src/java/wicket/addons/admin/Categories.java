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

import wicket.addons.ServiceLocator;

import wicket.AttributeModifier;
import wicket.Page;
import wicket.PageParameters;
import wicket.addons.BaseHtmlPage;
import wicket.addons.db.Category;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class Categories extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private static final String PAGE_TITLE = "Wicket-Addons: Maintain categories";

    private ListView table;
    
    /**
     * Constructor
     * @param parameters
      */
    public Categories(final PageParameters parameters)
    {
        super(parameters, PAGE_TITLE);
        
		List categories = ServiceLocator.instance().getCategoryService().getAllCategories();
        
        // Add table of existing comments
        this.table = new ListView("rows", categories)
        {
            public void populateItem(final ListItem listItem)
            {
                // alternating row styles
                listItem.add(new AttributeModifier("class",
                        new Model((listItem.getIndex() % 2) == 0 ? "even" : "odd")));
                
                final Category value = (Category) listItem.getModelObject();

                listItem.add(new MyPageLink("name", value, value.getName()));
                listItem.add(new MyPageLink("description", value, value.getDescription()));
                listItem.add(new MyPageLink("modified", value, value.getLastUpdated()));
                listItem.add(new MyPageLink("noOfAddons", value, new Integer(value.getAddonCount())));
            }
        };

        add(table);
        
        add(new PageLink("newCategory", AddOrModifyCategory.class));
        
        get("sidebarsRight").setVisible(false);
    }
    
    /**
     * @see wicket.Component#onRender(wicket.RequestCycle)
     */
    protected void onBeginRequest()
    {
		List categories = ServiceLocator.instance().getCategoryService().getAllCategories();
        this.table.setModelObject(categories);
    }
    
    private class MyPageLink extends PageLink
    {
        public MyPageLink(final String componentName, final Category category, final Serializable value)
        {
            super(componentName + "Link", new IPageLink()
	            {
	                public Page getPage()
	                {
	                    return new AddOrModifyCategory(category);
	                }
	
	                public Class getPageIdentity()
	                {
	                    return AddOrModifyCategory.class;
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
