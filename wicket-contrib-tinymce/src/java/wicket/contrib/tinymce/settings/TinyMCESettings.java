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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCESettings implements Serializable {

    private Mode mode;
    private Theme theme;
    private Location toolbarLocation;
    private Location statusbarLocation;
    private Align toolbarAlign;
    private boolean verticalResizing;
    private boolean horizontalResizing;

    private List plugins;
    private List controls;

    public TinyMCESettings() {
        this(Mode.textareas, Theme.simple);
    }

    public TinyMCESettings(Mode mode) {
        this(mode, Theme.simple);
    }

    public TinyMCESettings(Theme theme) {
        this(Mode.textareas, theme);
    }

    public TinyMCESettings(Mode mode, Theme theme) {
        this.mode = mode;
        this.theme = theme;
        controls = new LinkedList();
        plugins = new LinkedList();
    }

    public void setToolbarLocation(Location toolbarLocation) {
        this.toolbarLocation = toolbarLocation;
    }

    public void setStatusbarLocation(Location statusbarLocation) {
        this.statusbarLocation = statusbarLocation;
    }

    public void setToolbarAlign(Align toolbarAlign) {
        this.toolbarAlign = toolbarAlign;
    }

    public void setVerticalResizing(boolean resizing) {
        this.verticalResizing = resizing;
    }

    public void setHorizontalResizing(boolean horizontalResizing) {
        this.horizontalResizing = horizontalResizing;
    }

    public Theme getTheme() {
        return theme;
    }

    public void addControl(Control control) {
        controls.add(control);
    }

//    public void addControl(PluginControl control) {
//        plugins.add(control.getPlugin());
//        controls.add(control);
//    }

    public String toJavaScript() {
        if (Theme.simple.equals(theme)) {
            return getSimpleSettings();
        } else {
            return getAdvancedSettings();
        }

    }

    private String getAdvancedSettings() {
        StringBuffer buffer = new StringBuffer();

        // mode
        buffer.append("\n\tmode : ").append("\"").append(mode.getName()).append("\"");
        // theme
        buffer.append(",\n\t").append("theme : ").append("\"").append(theme.getName()).append("\"");

        // add additional controls
        addButtons1_Before(buffer);
        addButtons1_After(buffer);
        addButtons2_Before(buffer);
        addButtons2_After(buffer);
        addButtons3_Before(buffer);
        addButtons3_After(buffer);

        // toolbar, statusbar
        addToolbarLocation(buffer);
        addStatusbarLocation(buffer);
        addToolbarAlign(buffer);

        // resizing
        addVerticalResizing(buffer);
        addHorizontalLocation(buffer);

        return buffer.toString();
    }

    private void addButtons1_Before(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Control.Toolbar.first, Control.Position.before);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons1_add_before : ") .append("\"").append(collectionAsString(result)).append("\"");
        }
    }

    private void addButtons1_After(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Control.Toolbar.first, Control.Position.after);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons1_add : ") .append("\"").append(collectionAsString(result)).append("\"");
        }
    }

    private void addButtons2_Before(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Control.Toolbar.second, Control.Position.before);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons2_add_before: ") .append("\"").append(collectionAsString(result)).append("\"");
        }
    }

    private void addButtons2_After(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Control.Toolbar.second, Control.Position.after);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons2_add : ").append("\"").append(collectionAsString(result)).append("\"");
        }
    }

    private void addButtons3_Before(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Control.Toolbar.third, Control.Position.before);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons3_add_before : ").append("\"").append(collectionAsString(result)).append("\"");
        }
    }

    private void addButtons3_After(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Control.Toolbar.third, Control.Position.after);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons3_add : ").append("\"").append(collectionAsString(result)).append("\"");
        }
    }

    private String collectionAsString(Collection controls) {
        StringBuffer buffer = new StringBuffer();
        Iterator iterator = controls.iterator();
        while (iterator.hasNext()) {
            Control control = (Control) iterator.next();
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(control.getButton().toString());
        }
        return buffer.toString();
    }

    void addHorizontalLocation(StringBuffer buffer) {
        String value = horizontalResizing ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        buffer.append(",\n\t").append("theme_advanced_resize_horizontal : ").append(value);
    }

    void addVerticalResizing(StringBuffer buffer) {
        String value = verticalResizing ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        buffer.append(",\n\t").append("theme_advanced_resizing : ").append(value);
    }

    void addToolbarAlign(StringBuffer buffer) {
        if (toolbarAlign != null) {
            buffer.append(",\n\t").append("theme_advanced_toolbar_align : ").append("\"").append(toolbarAlign.getName()).append("\"");
        }
    }

    void addToolbarLocation(StringBuffer buffer) {
        if (toolbarLocation != null) {
            buffer.append(",\n\t").append("theme_advanced_toolbar_location : ").append("\"").append(toolbarLocation.getName()).append("\"");
        }
    }

    void addStatusbarLocation(StringBuffer buffer) {
        if (statusbarLocation != null) {
            buffer.append(",\n\t").append("theme_advanced_statusbar_location : ").append("\"").append(statusbarLocation.getName()).append("\"");
        }
    }

    private String getSimpleSettings() {
        StringBuffer buffer = new StringBuffer();

        // tinymce mode
        buffer.append("\n\tmode : ").append("\"").append(mode.getName()).append("\"").append(", ");

        // tinymce theme
        buffer.append("\n\ttheme : ").append("\"").append(theme.getName()).append("\"");

        return buffer.toString();
    }

    private class ControlPredicate implements Predicate {

        private Control.Toolbar toolbar;
        private Control.Position position;

        public ControlPredicate(Control.Toolbar toolbar, Control.Position position) {
            this.toolbar = toolbar;
            this.position = position;
        }

        public boolean evaluate(Object object) {
            Control control = (Control) object;
            return toolbar.equals(control.getToolbar()) && position.equals(control.getPosition());
        }
    }

    public static class Mode extends Enum {

        public static final Mode textareas = new Mode("textareas");

        private Mode(String name) {
            super(name);
        }
    }

    public static class Theme extends Enum {

        public static final Theme simple = new Theme("simple");
        public static final Theme advanced = new Theme("advanced");

        private Theme(String name) {
            super(name);
        }
    }

    public static class Location extends Enum {

        public static final Location top = new Location("top");
        public static final Location bottom = new Location("bottom");

        private Location(String name) {
            super(name);
        }
    }

    public static class Align extends Enum {

        public static final Align left = new Align("left");
        public static final Align center = new Align("center");
        public static final Align right = new Align("right");

        private Align(String name) {
            super(name);
        }
    }

}
