/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class FullScreenPlugin extends Plugin {

    private PluginButton fullscreenButton;

    public FullScreenPlugin() {
        super("fullscreen");
        fullscreenButton = new PluginButton("fullscreen", this);
    }

    public PluginButton getFullscreenButton() {
        return fullscreenButton;
    }
}
