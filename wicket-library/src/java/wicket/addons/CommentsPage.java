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
import java.util.ArrayList;
import java.util.List;

import wicket.Page;
import wicket.addons.db.Addon;
import wicket.addons.db.Comment;
import wicket.addons.models.AddonModel;
import wicket.addons.utils.CommentListEntry;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.navigation.paging.PagingNavigator;

/**
 * @author Juergen Donnerstag
 */
public final class CommentsPage extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private final AddonModel addonModel;
    
    /**
     * Constructor
     * @param parameters
     */
    public CommentsPage(final Addon addon)
    {
        super(null, "Addon specific comments");

        getUserService().lock(addon, 0);
        this.addonModel = new AddonModel(addon);
        addonModel.getComments().size();
        
        add(new Label("addonName", addonModel.getName()));
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

        final List comments = new CommentsDataList(addonModel);
        final PageableListView commentListView = new PageableListView("comments", comments, 10)
        {
            public void populateItem(final ListItem listItem)
            {
                final CommentListEntry value = new CommentListEntry("comment", (Comment)listItem.getModelObject());
                listItem.add(value);
            }
        };

        add(commentListView);
        add(new PagingNavigator("pageNavigation1", commentListView));
        add(new PagingNavigator("pageNavigation2", commentListView ));
    }
    
    public class CommentsDataList extends AbstractList implements Serializable
    {
        private final List comments;
        
        public CommentsDataList(final AddonModel addonModel)
        {
            // TODO getComment() to return a List
            this.comments = new ArrayList();
            this.comments.addAll(addonModel.getComments());
        }
        
		/**
		 * @see wicket.addons.utils.AbstractDataList#getInternalSize()
		 */
		public int size()
		{
		    return comments.size();
		}

		/**
		 * @see wicket.addons.utils.AbstractDataList#getInternalData(int, int)
		 */
		public Object get(int index)
		{
		    return comments.get(index);
		}
    }
}
