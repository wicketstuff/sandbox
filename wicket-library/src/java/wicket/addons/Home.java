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

import java.util.List;

import wicket.PageParameters;
import wicket.addons.dao.News;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * 
 * @author Juergen Donnerstag
 */
public final class Home extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    public Home()
    {
        this(null);
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public Home(final PageParameters parameters)
    {
        super(parameters, "Appfuse Homepage");
/*
        StringResourceModel model = new StringResourceModel("heading.welcome", this, null);
        add(new Label("heading", model.getString()));
*/        
        final List newsData = this.getAddonDao().getNews(0, 10);
        
        // News
        add(new ListView("newsList", newsData)
        {
            public void populateItem(final ListItem listItem)
            {
                final ContentNews value = new ContentNews("news",(News)listItem.getModelObject());
                listItem.add(value);
            }
        });
    }
}
