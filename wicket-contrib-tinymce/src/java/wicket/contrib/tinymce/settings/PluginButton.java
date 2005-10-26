/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.Plugin;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class PluginButton extends Button {

    private Plugin plugin;

    PluginButton(String name, Plugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
