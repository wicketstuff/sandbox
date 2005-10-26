/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TablePlugin extends Plugin {

    public PluginButton tableControls;

    public TablePlugin() {
        super("table");
        tableControls = new PluginButton("tablecontrols", this);
    }

    public PluginButton getTableControls() {
        return tableControls;
    }
}
