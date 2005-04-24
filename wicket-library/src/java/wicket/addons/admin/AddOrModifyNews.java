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

import wicket.Page;
import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.BaseHtmlPage;
import wicket.addons.ContentNews;
import wicket.addons.Home;
import wicket.addons.dao.News;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class AddOrModifyNews extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    final private Page me;
    final private WebMarkupContainer previewRegion;
    
    /**
     * Constructor
     */
    public AddOrModifyNews()
    {
        this(new News());
    }
    
    public AddOrModifyNews(final PageParameters paramters)
    {
        this(new News());
    }
    
    /**
     * Constructor
     * @param parameters
      */
    public AddOrModifyNews(News news)
    {
        super(null, "Wicket-Addons: Add and/or modify News Form");
        me = this;
        
        if (news == null)
        {
            news = new News();
        }
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new AddNewsForm("form", feedback, news));
        
        previewRegion = new WebMarkupContainer("previewRegion");
        add(previewRegion);
        previewRegion.setVisible(false);
        
        final ContentNews value = new ContentNews("news", news);
        
        previewRegion.add(value);
    }

    /**
     * 
     */
    public final class AddNewsForm extends Form
    {
        final News news;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public AddNewsForm(final String componentName, final FeedbackPanel feedback, final News news)
        {
            super(componentName, feedback);
            
            this.news = (News)getAddonDao().load(news);
            
            add(new TextField("headline", new PropertyModel(news, "headline")));
            add(new TextArea("message", new PropertyModel(news, "message")));
            
            WebMarkupContainer button = new WebMarkupContainer("delete");
            add(button);
            
            WebMarkupContainer previewButton = new WebMarkupContainer("preview");
            add(previewButton);
            
            if (news.getId() == 0)
            {
                button.setVisible(false);
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
                getAddonDao().saveOrUpdate(news);
            }
            else if (cycle.getRequest().getParameter("delete") != null)
            {
                getAddonDao().delete(news);
            }
            else if (cycle.getRequest().getParameter("preview") != null)
            {
                previewRegion.setVisible(true);
                cycle.setResponsePage(me);
                cycle.setRedirect(false);
                return;
            }
            
            cycle.setResponsePage(new Home(null));
        }
    }
}
