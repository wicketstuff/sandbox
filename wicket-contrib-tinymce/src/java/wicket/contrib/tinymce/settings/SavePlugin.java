/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SavePlugin extends Plugin {

    private PluginButton saveButton;

    public SavePlugin() {
        super("save");
        saveButton = new PluginButton("save", this);
    }

    public PluginButton getSaveButton() {
        return saveButton;
    }
}
