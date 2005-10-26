/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SearchReplacePlugin extends Plugin {

    private PluginButton searchButton;
    private PluginButton replaceButton;

    public SearchReplacePlugin() {
        super("searchreplace");
        searchButton = new PluginButton("search", this);
        replaceButton = new PluginButton("replace", this);
    }

    public PluginButton getSearchButton() {
        return searchButton;
    }

    public PluginButton getReplaceButton() {
        return replaceButton;
    }
}
