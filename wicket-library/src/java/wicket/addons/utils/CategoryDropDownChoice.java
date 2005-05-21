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

import wicket.addons.dao.Category;
import wicket.addons.dao.IAddonDao;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.model.ChoiceList;
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
    public CategoryDropDownChoice(final String id, final IAddonDao dao)
    {
        super(id, new Model(""), new ChoiceList(new ArrayList()));
        
        final List categoryCount = dao.getCountByCategory();
        final List categoryOptions = new ArrayList();

        categoryOptions.add(new CategoryOption("All categories", -1));
        
        final Iterator iter = categoryCount.iterator();
        while (iter.hasNext())
        {
            final Category cat = (Category)iter.next();
            categoryOptions.add(new CategoryOption(cat.getName() + "  (" + cat.getCount() + ")", cat.getId()));
        }

        ((ChoiceList)this.getChoices()).addAll(categoryOptions);
    }
    
	public class CategoryOption implements Serializable
	{
	    private final String text;
	    private final int categoryId;
	    
	    public CategoryOption(final String text, final int categoryId)
	    {
	        this.text = text;
	        this.categoryId = categoryId;
	    }
	    
	    public int getCategoryId()
	    {
	        return categoryId;
	    }
	    
	    public String toString()
	    {
	        return text;
	    }
	}
}
