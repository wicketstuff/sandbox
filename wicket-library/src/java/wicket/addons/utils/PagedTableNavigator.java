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

import wicket.markup.html.list.PageableListView;
import wicket.markup.html.list.PageableListViewNavigation;
import wicket.markup.html.list.PageableListViewNavigationIncrementLink;
import wicket.markup.html.panel.Panel;

/**
 * A Wicket panel component to draw and maintain a complete
 * page navigator, meant to be easily added to any table. A navigation
 * which contains links to the first and last page, the current page
 * +- some increment and which supports paged navigation bars 
 * (@see TableNavigationWithMargin).
 *  
 * @author Juergen Donnerstag
 */
public class PagedTableNavigator extends Panel 
{
    /** The navigation bar to be printed, e.g. 1 | 2 | 3 etc. */
    private final PageableListViewNavigation tableNavigation;

    private final PageableListViewNavigationIncrementLink prevLink;
    private final PageableListViewNavigationIncrementLink nextLink;

    private final PageableListView table;
    
    /**
     * Constructor.
     * @param componentName The component's name
     * @param table The table the page links are referring to.
     */
    public PagedTableNavigator(final String componentName, final PageableListView table)
    {
        super(componentName);
        this.table = table;
        
        // Get the navigation bar and add it to the hierarchy
        this.tableNavigation = newTableNavigation(table);
        add(tableNavigation);
        
        // Add additional page links
        prevLink = new PageableListViewNavigationIncrementLink("prev", table, -1)
        {
            public void onBeginRender()
            {
                setVisible(table.getCurrentPage() > 0);
            }
        };
        
        nextLink = new PageableListViewNavigationIncrementLink("next", table, 1)
        {
            public void onBeginRender()
            {
                setVisible(table.getCurrentPage() < (table.getPageCount() - 1));
            }
        };
        
        add(prevLink);
        add(nextLink);
    }

    /**
     * Create a new TableNavigation. May be subclassed to make us of
     * specialized TableNavigation.
     * 
     * @param table
     * @return table navigation object
     */
    protected PageableListViewNavigation newTableNavigation(final PageableListView table)
    {
        return new PageableListViewNavigation("navigation", table);
    }
}