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
package wicket.addons.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.addons.ServiceLocator;
import wicket.addons.db.Category;
import wicket.markup.html.form.ChoiceRenderer;
import wicket.markup.html.form.DropDownChoice;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public class CategoryDropDownChoice extends DropDownChoice
{
    /**
     * Constructor
     * @param parameters
      */
    public CategoryDropDownChoice(final String id, final Long categoryId)
    {
        super(id);

        setChoiceRenderer(new ChoiceRenderer());

        final List categoryCount = ServiceLocator.instance().getCategoryService().getAllCategories();
        final List categoryOptions = new ArrayList();

        final CategoryOption allOption = new CategoryOption("All categories", null);
        setModel(new Model(allOption));
        categoryOptions.add(allOption);
        
        final Iterator iter = categoryCount.iterator();
        while (iter.hasNext())
        {
            final Category cat = (Category)iter.next();
            categoryOptions.add(new CategoryOption(cat.getName() + "  (" + cat.getAddonCount() + ")", cat));
        }

        this.setChoices(categoryOptions);
        
        for (Object data : categoryOptions)
        {
            final CategoryOption option = (CategoryOption) data;
            if ((option.getCategory() != null) && (option.getCategory().getId() != null) && 
                    (categoryId != null) && (option.getCategory().getId().doubleValue() == categoryId.doubleValue()))
            {
                setModelObject(option);
                break;
            }
        }
    }
    
	public class CategoryOption implements Serializable
	{
	    private final String text;
	    private final Category category;
	    
	    public CategoryOption(final String text, final Category category)
	    {
	        this.text = text;
	        this.category = category;
	    }
	    
	    public Category getCategory()
	    {
	        return category;
	    }
	    
	    public String toString()
	    {
	        return text;
	    }
	}
}
