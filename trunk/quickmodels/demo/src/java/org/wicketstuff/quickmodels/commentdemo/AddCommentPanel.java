/* Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.quickmodels.commentdemo;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.quickmodels.DbPanel;
import org.wicketstuff.quickmodels.ModelBuilder;
import org.wicketstuff.quickmodels.PojoModel;
import org.wicketstuff.quickmodels.Queries;

/**
 * Form which adds a comment to the database
 *
 * @author Tim Boudreau
 */
final class AddCommentPanel extends DbPanel<Comment> {
    AddCommentPanel(String id) {
        super (id);
        ModelBuilder<Comment, ?> builder =
                Queries.NEW_OBJECT.builder(Comment.class);
        
        setModel (builder.single(getDatabase()));
        CommentForm form = new CommentForm ("commentform");
        add (form);
        FeedbackPanel feedback = new FeedbackPanel ("feedback");
        add (feedback);
    }
    
    private final class CommentForm extends Form {
        private final TextField nameField = new TextField ("name");
        private final TextArea commentArea = new TextArea ("comment");
        CommentForm (String id) {
            super (id);
            add (nameField);
            add (commentArea);
        }

        @Override
        protected void onSubmit() {
            if (nameField.getModelObject() == null) {
                error ("Enter your name");
                return;
            }
            if (commentArea.getModelObject() == null) {
                error ("Enter a comment");
                return;
            }
            PojoModel<Comment> mdl = (PojoModel<Comment>) 
                    AddCommentPanel.this.getModel();
            mdl.modified();
            mdl.save();
            //Force a new instance of the home page, so that the 
            //visibility of the comments panel is updated
            setResponsePage(Home.class);
        }
    }
}
