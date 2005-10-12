/*
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
package wicket.contrib.tinymce.settings;

import java.io.Serializable;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class Control implements Serializable {

    private Button button;
    private Toolbar toolbar;
    private Position position;

    public Control(Button button, Toolbar toolbar, Position position) {
        this.button = button;
        this.toolbar = toolbar;
        this.position = position;
    }

    public Button getButton() {
        return button;
    }

    public Control.Toolbar getToolbar() {
        return toolbar;
    }

    public Control.Position getPosition() {
        return position;
    }

    public static class DefaultButton extends Button {

        public static final DefaultButton bold = new DefaultButton("bold");
        public static final DefaultButton italic = new DefaultButton("italic");
        public static final DefaultButton underline = new DefaultButton("underline");
        public static final DefaultButton strikethrough = new DefaultButton("strikethrough");
        public static final DefaultButton justifyleft = new DefaultButton("justifyleft");
        public static final DefaultButton justifycenter = new DefaultButton("justifycenter");
        public static final DefaultButton justifyright = new DefaultButton("justifyright");
        public static final DefaultButton justifyfull = new DefaultButton("justifyfull");
        public static final DefaultButton styleselect = new DefaultButton("styleselect");
        public static final DefaultButton formatselect = new DefaultButton("formatselect");
        public static final DefaultButton bullist = new DefaultButton("bullist");
        public static final DefaultButton numlist = new DefaultButton("numlist");
        public static final DefaultButton outdent = new DefaultButton("outdent");
        public static final DefaultButton indent = new DefaultButton("indent");
        public static final DefaultButton undo = new DefaultButton("undo");
        public static final DefaultButton redo = new DefaultButton("redo");
        public static final DefaultButton link = new DefaultButton("link");
        public static final DefaultButton unlink = new DefaultButton("unlink");
        public static final DefaultButton anchor = new DefaultButton("anchor");
        public static final DefaultButton image = new DefaultButton("image");
        public static final DefaultButton cleanup = new DefaultButton("cleanup");
        public static final DefaultButton help = new DefaultButton("help");
        public static final DefaultButton code = new DefaultButton("code");
        public static final DefaultButton hr = new DefaultButton("hr");
        public static final DefaultButton removeformat = new DefaultButton("removeformat");
        public static final DefaultButton visualaid = new DefaultButton("visualaid");
        public static final DefaultButton sub = new DefaultButton("sub");
        public static final DefaultButton sup = new DefaultButton("sup");
        public static final DefaultButton charmap = new DefaultButton("charmap");
        public static final DefaultButton separator = new DefaultButton("separator");

        public DefaultButton(String name) {
            super(name);
        }
    }

    public static class Position extends Enum {

        public static final Position before = new Position("before");
        public static final Position after = new Position("after");

        public Position(String name) {
            super(name);
        }
    }

    public static class Toolbar extends Enum {

        public static final Toolbar first = new Toolbar("first");
        public static final Toolbar second = new Toolbar("second");
        public static final Toolbar third = new Toolbar("third");

        public Toolbar(String name) {
            super(name);
        }
    }
}
