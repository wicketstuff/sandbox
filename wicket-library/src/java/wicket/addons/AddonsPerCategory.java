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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.dao.AddonDaoImpl;
import wicket.addons.dao.Category;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class AddonsPerCategory extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private static Log log = LogFactory.getLog(AddonsPerCategory.class);
    
    /**
     * Constructor
     * @param parameters
      */
    public AddonsPerCategory(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Add-ons per category");

        log.info("Page parameters: " + parameters);
        
        add(new SelectForm("form"));
    }
    
    private final List loadCategoryCount()
    {
        BeanFactory fac = ((AddonApplication)RequestCycle.get().getApplication()).getBeanFactory();
        AddonDaoImpl dao = (AddonDaoImpl) fac.getBean("AddonDao");
        return dao.getCountByCategory();
    }
    
    public final class SelectForm extends Form
    {
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public SelectForm(final String componentName)
        {
            super(componentName, null);
            
            List categories = loadCategoryCount();
            List values = new ArrayList();

            final Iterator iter = categories.iterator();
            while (iter.hasNext())
            {
    	        final Category category = (Category)iter.next();
    	
    	        final String text = category.getName() + "  (" + category.getCount() + ")";
    	        values.add(text);
            }
            
            add(new DropDownChoice("category", new Model(""), values));
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            Map params = cycle.getRequest().getParameterMap();
            if (cycle.getRequest().getParameter("xxx") == "xxx")
            {
                ; 
            }
            
            cycle.setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
}
