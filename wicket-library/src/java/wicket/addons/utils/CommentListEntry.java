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
package wicket.addons.utils;

import java.text.SimpleDateFormat;

import wicket.addons.hibernate.Comment;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;

/**
 * @author Juergen Donnerstag
 */
public class CommentListEntry extends Panel
{
    /**
     * Constructor
     * @param parameters
      */
    public CommentListEntry(final String componentName, final Comment comment)
    {
        super(componentName);

        add(new Label("who", comment.getUser().getNickname()));
        add(new Label("when", new SimpleDateFormat().format(comment.getLastModified())));
        add(new Label("comment", comment.getComment()));
    }
}
