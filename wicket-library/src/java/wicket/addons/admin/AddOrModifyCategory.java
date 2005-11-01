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
import wicket.addons.db.Category;
import wicket.addons.db.User;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class AddOrModifyCategory extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     */
    public AddOrModifyCategory()
    {
        this(null);
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public AddOrModifyCategory(final Category category)
    {
        super(null, "Wicket-Addons: Category Request Form");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        add(new AddCategoryForm("form", category));
    }

    /**
     * 
     */
    public final class AddCategoryForm extends Form
    {
        private Category category;
        private String creator;
        
        /**
         * Constructor
         * @param componentName Name of form
         */
        public AddCategoryForm(final String componentName, Category category)
        {
            super(componentName);

            if (category == null)
            {
                category = Category.Factory.newInstance();
            }
            this.category = category;
            
            add(new TextField("categoryName", new PropertyModel(category, "name")));
            add(new TextArea("description", new PropertyModel(category, "description")));
            
            WebMarkupContainer note = new WebMarkupContainer("deleteNote");
            add(note);
            
            WebMarkupContainer button = new WebMarkupContainer("delete");
            add(button);
            
            if (category.getAddonCount() > 0)
            {
                button.setVisible(false);
            }
            else
            {
                note.setVisible(false);
            }
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            final Map param = cycle.getRequest().getParameterMap();
            
            if (cycle.getRequest().getParameter("save") != null)
            {
                final User user = getUser();
                category.setCreatedBy(user);
                
                if (category.getId() == null)
                {
                    category.setEnable(true);
                    ServiceLocator.instance().getCategoryService().addCategegory(category);
                }
                else
                {
                    ServiceLocator.instance().getCategoryService().save(category);
                }
            }
            else if (cycle.getRequest().getParameter("delete") != null)
            {
        		ServiceLocator.instance().getCategoryService().remove(category);
            }
            
            cycle.setResponsePage(new Categories(null));
        }
        
        public String getCreator()
        {
            return creator;
        }
        
        public void setCreator(final String creator)
        {
            this.creator = creator;
        }
    }
    
}
