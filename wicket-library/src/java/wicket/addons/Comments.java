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

import wicket.Page;
import wicket.addons.dao.Addon;
import wicket.addons.dao.Comment;
import wicket.addons.utils.CommentListEntry;
import wicket.addons.utils.PagedTableNavigator;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.PageableListView;

/**
 * @author Juergen Donnerstag
 */
public final class Comments extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private final Addon addon;
    
    /**
     * Constructor
     * @param parameters
     */
    public Comments(final Addon addon)
    {
        super(null, "Addon specific comments");

        this.addon = addon;
        
        add(new Label("addonName", addon.getName()));
        add(new PageLink("addComment", new IPageLink() 
		        {
		            public Page getPage()
		            {
		                return new AddComment(addon);
		            }
		            
		            public Class getPageIdentity()
		            {
		                return AddComment.class;
		            }
		        }));
        
        add(new PageLink("details", new IPageLink() 
                {
                    public Page getPage()
                    {
                        return new PluginDetails(addon);
                    }
                    
                    public Class getPageIdentity()
                    {
                        return PluginDetails.class;
                    }
                }));

        final List comments = new CommentsDataList();
        final PageableListView commentListView = new PageableListView("comments", comments, 10)
        {
            public void populateItem(final ListItem listItem)
            {
                final CommentListEntry value = new CommentListEntry("comment", (Comment)listItem.getModelObject());
                listItem.add(value);
            }
        };

        add(commentListView);
        add(new PagedTableNavigator("pageNavigation1", commentListView));
        add(new PagedTableNavigator("pageNavigation2", commentListView ));
    }
    
    public class CommentsDataList extends AbstractList implements Serializable
    {
		/**
		 * @see wicket.addons.utils.AbstractDataList#getInternalSize()
		 */
		public int size()
		{
		    getAddonDao().update(addon);
		    return addon.getComments().size();
		}

		/**
		 * @see wicket.addons.utils.AbstractDataList#getInternalData(int, int)
		 */
		public Object get(int index)
		{
		    getAddonDao().update(addon);
		    return addon.getComments().get(index);
		}
    }
}
