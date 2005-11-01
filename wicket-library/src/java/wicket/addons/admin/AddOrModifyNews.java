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
import wicket.addons.ServiceLocator;
import wicket.addons.db.News;
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
        this(News.Factory.newInstance());
    }
    
    public AddOrModifyNews(final PageParameters paramters)
    {
        this(News.Factory.newInstance());
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
            news = News.Factory.newInstance();
        }
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new AddNewsForm("form", news));
        
        previewRegion = new WebMarkupContainer("previewRegion");
        add(previewRegion);
        previewRegion.setVisible(false);
        
        final ContentNews value = new ContentNews("news", news);
        
        previewRegion.add(value);
        
        get("sidebarsRight").setVisible(false);
    }

    /**
     * 
     */
    public final class AddNewsForm extends Form
    {
        private final News news;
        
        /**
         * Constructor
         * @param componentName Name of form
         */
        public AddNewsForm(final String componentName, final News news)
        {
            super(componentName);
            this.news = news;
            
            add(new TextField("headline", new PropertyModel(news, "headLine")));
            add(new TextArea("message", new PropertyModel(news, "message")));
            
            WebMarkupContainer button = new WebMarkupContainer("delete");
            add(button);
            
            WebMarkupContainer previewButton = new WebMarkupContainer("preview");
            add(previewButton);
            
            if (news.getId() == null)
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
                if (news.getId() == null)
                {
                    ServiceLocator.instance().getNewsService().addNews(news);
                }
                else
                {
                    ServiceLocator.instance().getNewsService().save(news);
                }
            }
            else if (cycle.getRequest().getParameter("delete") != null)
            {
                ServiceLocator.instance().getNewsService().remove(news);
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
