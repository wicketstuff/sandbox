/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class ZoomPlugin extends Plugin {

    private PluginButton zoomButton;

    public ZoomPlugin() {
        super("zoom");
        zoomButton = new PluginButton("zoom", this);
    }

    public PluginButton getZoomButton() {
        return zoomButton;
    }
}
