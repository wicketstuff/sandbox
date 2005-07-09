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
import wicket.addons.hibernate.Category;
import wicket.addons.hibernate.IAddonDao;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.link.Link;
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
    private static int allCategoryCount;
    
    private ListView table;
    private Label allCount;

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
            protected void populateItem(ListItem listItem)
            {
                final Category category = (Category)listItem.getModelObject();

                final int mod = listItem.getIndex() % 2;
                String text = (mod == 0 ? "<tr>" : "");
                
                final String href = getPage().urlFor("0", NewAndUpdatedAddons.class, new PageParameters("category=" + category.getId()));
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
        
        final Link hrefAll = new BookmarkablePageLink("allHref", NewAndUpdatedAddons.class, new PageParameters("category=-1"));
        add(hrefAll);
        
        this.allCount = new Label("allCount", Integer.toString(allCategoryCount));
        hrefAll.add(allCount);
    }
    
    private List loadCategoryCount()
    {
        if ((categoryCount != null) && (nextUpdate > System.currentTimeMillis()))
        {
            return categoryCount;
        }

        final IAddonDao dao = this.getAddonDao();
        nextUpdate = System.currentTimeMillis() + updateIntervall;

        allCategoryCount = 0;
        List data = dao.getCountByCategory();
        for (Object obj : data)
        {
            Category cat = (Category) obj;
            allCategoryCount += cat.getCount();
        }
        
        return data;
    }
    
    /**
     * @see wicket.Component#onRender(wicket.RequestCycle)
     */
    protected void onBeginRequest()
    {
        categoryCount = new AlternatingList(loadCategoryCount());

        // TODO the model could be detachable
        table.modelChanging();
        table.setModelObject(categoryCount);
        table.modelChanged();
        
        allCount.modelChanging();
        allCount.setModelObject(Integer.toString(allCategoryCount));
        allCount.modelChanged();
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
