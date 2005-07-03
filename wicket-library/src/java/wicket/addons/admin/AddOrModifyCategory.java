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
import wicket.addons.dao.Category;
import wicket.addons.dao.User;
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
        this(0);
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public AddOrModifyCategory(final int categoryId)
    {
        super(null, "Wicket-Addons: Category Request Form");
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new AddCategoryForm("form", feedback, categoryId));
    }

    /**
     * 
     */
    public final class AddCategoryForm extends Form
    {
        private final Category category;
        private String creator;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public AddCategoryForm(final String componentName, final FeedbackPanel feedback, final int categoryId)
        {
            super(componentName, feedback);

            if (categoryId == 0)
            {
                this.category = new Category();
            }
            else
            {
                this.category = (Category)getAddonDao().load(Category.class, new Integer(categoryId));
                getAddonDao().getCountByCategory();
            }
            
            add(new TextField("categoryName", new PropertyModel(category, "name")));
            add(new TextArea("description", new PropertyModel(category, "description")));
            
            WebMarkupContainer note = new WebMarkupContainer("deleteNote");
            add(note);
            
            WebMarkupContainer button = new WebMarkupContainer("delete");
            add(button);
            
            if (category.getCount() > 0)
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
                getAddonDao().saveOrUpdate(category);
            }
            else if (cycle.getRequest().getParameter("delete") != null)
            {
                getAddonDao().delete(category);
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
