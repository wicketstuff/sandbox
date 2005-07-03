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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wicket.IFeedback;
import wicket.Page;
import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.dao.Addon;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Juergen Donnerstag
 */
public final class Search extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private List results = new ArrayList();
    
    /**
     * Constructor
     * @param parameters
      */
    public Search(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Search page");
        
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        add(new SearchForm("form", feedback));
        
        add(new ListView("results", results)
        {
			protected void populateItem(ListItem item)
			{
			    final AddonAndScore addon = (AddonAndScore) item.getModelObject();
			    
			    item.add(new Label("score", "" + addon.getScore()));
			    item.add(new Label("name", addon.getAddon().getName()));
			    item.add(new Label("category", addon.getAddon().getCategory().getName()));
			    item.add(new Label("version", addon.getAddon().getVersion()));
			    item.add(new Label("description", addon.getAddon().getDescription()));

		        item.add(new PageLink("details", new IPageLink()
		        {
		            public Page getPage()
		            {
		                return new PluginDetails(addon.getAddon().getId());
		            }
		            
		            public Class getPageIdentity()
		            {
		                return PluginDetails.class;
		            }
		        }));
			}
        });
    }

    public final class SearchForm extends Form
    {
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public SearchForm(final String componentName, final IFeedback feedback)
        {
            super(componentName, feedback);
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final RequestCycle cycle = getRequestCycle();
            
            Search.this.get("results").modelChanging();
            
            // This is an example on how to get input from an <input>
            // even without a Wicket TextField. 
            final Map params = cycle.getRequest().getParameterMap();
            final String searchText = (String)params.get("query"); // the "name" of the input field
            if ((searchText != null) && (searchText.trim().length() > 0))
            {
                final List data = getAddonDao().searchAddon(searchText, 20);
                results.clear();
                for (Object obj : data)
                {
                    final AddonAndScore addon = new AddonAndScore((Object[]) obj);
                    results.add(addon);
                }
            }
            else
            {
                results.clear();
            }

            Search.this.get("results").modelChanged();
            
            // setResponsePage(newPage(getApplication().getPages().getHomePage()));
        }
    }
    
    private final class AddonAndScore implements Serializable
    {
        private double score;
        private Addon addon;
        
        public AddonAndScore(final Object[] data)
        {
            this.score = ((Double)data[0]).doubleValue();
            this.addon = (Addon)data[1];
        }
        
        public double getScore()
        {
            return score;
        }
        
        public Addon getAddon()
        {
            return addon;
        }
    }
}
