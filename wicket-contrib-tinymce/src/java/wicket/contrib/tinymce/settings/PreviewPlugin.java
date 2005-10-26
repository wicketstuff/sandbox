/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class PreviewPlugin extends Plugin {

    private PluginButton previewButton;

    public PreviewPlugin() {
        super("preview");
        previewButton = new PluginButton("preview", this);
    }

    public PluginButton getPreviewButton() {
        return previewButton;
    }
}
