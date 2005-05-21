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

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

import wicket.PageParameters;
import wicket.addons.dao.Category;
import wicket.addons.dao.IAddonDao;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * @author Juergen Donnerstag
 */
public final class Categories extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    // A better solution would be a trigger which informs the cache that it needs to update
    private static long updateIntervall = 10 * 1000; // every 10 seconds
    private static long nextUpdate;
    private static List categoryCount;

    private ListView table;

    /**
     * Constructor
     * @param parameters
      */
    public Categories(final PageParameters parameters)
    {
        super(parameters, "Wicket Addons: List of Categories");
        addComponents();
    }
    
    private void addComponents()
    {
        // TODO make it a detachable model
        this.table = new ListView("table", (List)null)
        {
/*            
        	protected IModel getListItemModel(final IModel listViewModel, final int index)
        	{
                final int size = ((List)listViewModel.getObject(null)).size();
                final int idx;
                if ((index % 2) == 0)
                {
                    idx = index / 2;
                }
                else
                {
                    idx = (size + index) / 2;
                }
                
        		return new ListItemModel(listViewModel, idx);
        	}
*/            
            protected void populateItem(ListItem listItem)
            {
                final Category category = (Category)listItem.getModelObject();

                final int mod = listItem.getIndex() % 2;
                String text = (mod == 0 ? "<tr>" : "");
                
                final String href = getPage().urlFor("0", AddonsPerCategory.class, new PageParameters("category=" + category.getId()));
                text += "<td width=\"50%\"><a class=\"lm\" href=\"" + href + "\">" + category.getName() + "  (" + category.getCount() + ")</a></td>";
                    
                if ((mod != 0) || (categoryCount.size() == (listItem.getIndex() + 1)) && ((categoryCount.size() % 2) != 0))
                {
                    text += "</tr>";
                }
                
                final Label label = new Label("text", text);
                label.setShouldEscapeModelStrings(false);
                listItem.add(label);
            }
        };
        
        add(this.table);
    }
    
    private List loadCategoryCount()
    {
        if ((categoryCount != null) && (nextUpdate > System.currentTimeMillis()))
        {
            return categoryCount;
        }

        final IAddonDao dao = this.getAddonDao();
        nextUpdate = System.currentTimeMillis() + updateIntervall;
        
        return dao.getCountByCategory();
    }
    
    /**
     * @see wicket.Component#onRender(wicket.RequestCycle)
     */
    protected void onBeginRequest()
    {
        // TODO strange; onBeginRequest() wir nicht bei BookmarkableLinks aufgerufen
        categoryCount = new AlternatingList(loadCategoryCount());

        // TODO the model could be detachable
        table.setModelObject(categoryCount);
    }
    
    class AlternatingList extends AbstractList implements Serializable
    {
        private final List list;
        
        public AlternatingList(final List list)
        {
            this.list = list;
        }
        
		/* (non-Javadoc)
		 * @see java.util.AbstractList#get(int)
		 */
		public Object get(int index)
		{
            final int size = list.size();
            final int idx;
            if ((index % 2) == 0)
            {
                idx = index / 2;
            }
            else
            {
                idx = (size + index) / 2;
            }
            
    		return list.get(idx);
		}

		/* (non-Javadoc)
		 * @see java.util.AbstractCollection#size()
		 */
		public int size()
		{
			return list.size();
		}
        
    }
}
