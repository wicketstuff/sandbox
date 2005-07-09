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

import java.text.SimpleDateFormat;
import java.util.Locale;

import wicket.addons.hibernate.News;
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
        final String dateString = dateFormat.format(news.getLastModified());
        
        add(new Label("date", dateString));
        add(new Label("headline", new PropertyModel(news, "headline")));
        add(new Label("message", new PropertyModel(news, "message")).setShouldEscapeModelStrings(false));
    }
}
