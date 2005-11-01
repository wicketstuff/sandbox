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

import java.util.ArrayList;
import java.util.List;

import wicket.addons.db.Rating;
import wicket.addons.db.User;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class MyRatings extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public MyRatings()
    {
        super(null, "Wicket-Addons: Ratings");
        User user = getUser();
        
        List myRatings = null;
        if (user != null)
        {
            myRatings = new ArrayList();
            myRatings.addAll(user.getRatings());
        }
        
        add(new ListView("rows", myRatings)
        {
			protected void populateItem(final ListItem listItem)
			{
                final Rating value = (Rating)listItem.getModelObject();
                listItem.add(new Label("plugin", value.getAddon().getName()));
                listItem.add(new Label("avg", new Model(new Double(5.5))));
                listItem.add(new Label("myRating", new Model(new Integer(value.getRating()))));
                listItem.add(new Label("comment", value.getComment()));
			}
        });
    }
}
