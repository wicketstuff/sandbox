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
package wicket.addons.sidebars;

import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.panel.Panel;

/**
 * @author Juergen Donnerstag
 */
public final class SidebarAdminMenu extends Panel
{
    /**
     * Constructor
     * @param parameters
      */
    public SidebarAdminMenu(final String componentName)
    {
        super(componentName);

        // I couldn't use [autolink] because the admin-pages
        // are in a subdirectory. And autolink allows only
        // to access subpackages but not parent packages.
        add(new BookmarkablePageLink("users", wicket.addons.admin.Users.class));
        add(new BookmarkablePageLink("categories", wicket.addons.admin.Categories.class));
        add(new BookmarkablePageLink("news", wicket.addons.admin.AddOrModifyNews.class));
        add(new BookmarkablePageLink("addons", wicket.addons.admin.AddOrModifyAddon.class));
    }
}
