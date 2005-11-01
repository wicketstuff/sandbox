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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import wicket.addons.db.News;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public class ContentNews extends Panel
{
    private final News news;
    
    /**
     * Constructor
     * @param parameters
      */
    public ContentNews(final String componentName, final News news)
    {
        super(componentName);
        this.news = news;
        
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.US);
        Timestamp lastUpdated = news.getLastUpdated();
        if (lastUpdated == null)
        {
            lastUpdated = new Timestamp(System.currentTimeMillis());
        }
        final String dateString = dateFormat.format(lastUpdated);
        
        add(new Label("date", dateString));
        add(new Label("headline", new PropertyModel(news, "headLine")));
        add(new Label("message", new PropertyModel(news, "message")).setEscapeModelStrings(false));
    }
}
