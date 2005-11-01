/*
 *  Copyright (C) 2005  Iulian-Corneliu Costan
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package wicket.contrib.tinymce.settings;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.set.ListOrderedSet;

import java.io.Serializable;
import java.util.*;

/**
 * Settings class for TinyMCE editor.
 *
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

    private Set plugins;
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
        plugins = new ListOrderedSet();
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

    public void add(Button button, Toolbar toolbar, Position position) {
        controls.add(new Control(button, toolbar, position));
    }

    public void add(PluginButton button, Toolbar toolbar, Position position) {
        register(button.getPlugin());
        controls.add(new Control(button, toolbar, position));
    }

    public void register(Plugin plugin) {
        plugins.add(plugin);
    }

    // used in testing
    Set getPlugins() {
        return plugins;
    }

    //todo change access
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

        // plugins
        addPlugins(buffer);

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
        addHorizontalResizing(buffer);

        return buffer.toString();
    }

    void addPlugins(StringBuffer buffer) {
        if (plugins.size() > 0) {
            String value = enumAsString(plugins);
            buffer.append(",\n\t").append("plugins : ") .append("\"").append(value).append("\"");
        }
    }

    private void addButtons1_Before(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Toolbar.first, Position.before);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons1_add_before : ") .append("\"").append(controlsAsString(result)).append("\"");
        }
    }

    private void addButtons1_After(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Toolbar.first, Position.after);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons1_add : ") .append("\"").append(controlsAsString(result)).append("\"");
        }
    }

    private void addButtons2_Before(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Toolbar.second, Position.before);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons2_add_before: ") .append("\"").append(controlsAsString(result)).append("\"");
        }
    }

    private void addButtons2_After(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Toolbar.second, Position.after);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons2_add : ").append("\"").append(controlsAsString(result)).append("\"");
        }
    }

    private void addButtons3_Before(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Toolbar.third, Position.before);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons3_add_before : ").append("\"").append(controlsAsString(result)).append("\"");
        }
    }

    private void addButtons3_After(StringBuffer buffer) {
        ControlPredicate predicate = new ControlPredicate(Toolbar.third, Position.after);
        Collection result = CollectionUtils.select(controls, predicate);
        if (result.size() > 0) {
            buffer.append(",\n\t").append("theme_advanced_buttons3_add : ").append("\"").append(controlsAsString(result)).append("\"");
        }
    }

    private String controlsAsString(Collection controls) {
        List buttons = new ArrayList();
        Iterator iterator = controls.iterator();
        while (iterator.hasNext()) {
            Control control = (Control) iterator.next();
            buttons.add(control.getButton());
        }
        return enumAsString(buttons);
    }

    private String enumAsString(Collection enums) {
        StringBuffer buffer = new StringBuffer();
        Iterator iterator = enums.iterator();
        while (iterator.hasNext()) {
            wicket.contrib.tinymce.settings.Enum enumObject = (Enum) iterator.next();
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(enumObject.getName());
        }
        return buffer.toString();
    }

    void addHorizontalResizing(StringBuffer buffer) {
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

        private Toolbar toolbar;
        private Position position;

        public ControlPredicate(Toolbar toolbar, Position position) {
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

    public static final Button bold = new Button("bold");
    public static final Button italic = new Button("italic");
    public static final Button underline = new Button("underline");
    public static final Button strikethrough = new Button("strikethrough");
    public static final Button justifyleft = new Button("justifyleft");
    public static final Button justifycenter = new Button("justifycenter");
    public static final Button justifyright = new Button("justifyright");
    public static final Button justifyfull = new Button("justifyfull");
    public static final Button styleselect = new Button("styleselect");
    public static final Button formatselect = new Button("formatselect");
    public static final Button bullist = new Button("bullist");
    public static final Button numlist = new Button("numlist");
    public static final Button outdent = new Button("outdent");
    public static final Button indent = new Button("indent");
    public static final Button undo = new Button("undo");
    public static final Button redo = new Button("redo");
    public static final Button link = new Button("link");
    public static final Button unlink = new Button("unlink");
    public static final Button anchor = new Button("anchor");
    public static final Button image = new Button("image");
    public static final Button cleanup = new Button("cleanup");
    public static final Button help = new Button("help");
    public static final Button code = new Button("code");
    public static final Button hr = new Button("hr");
    public static final Button removeformat = new Button("removeformat");
    public static final Button visualaid = new Button("visualaid");
    public static final Button sub = new Button("sub");
    public static final Button sup = new Button("sup");
    public static final Button charmap = new Button("charmap");
    public static final Button separator = new Button("separator");

    // others
    public static final Button newdocument = new Button("newdocument");
    public static final Button cut = new Button("cut");
    public static final Button copy = new Button("copy");
    public static final Button fontselect = new Button("fontselect");
    public static final Button fontsizeselect = new Button("fontsizeselect");
    public static final Button forecolor = new Button("forecolor");
    public static final Button backcolor = new Button("backcolor");
}
