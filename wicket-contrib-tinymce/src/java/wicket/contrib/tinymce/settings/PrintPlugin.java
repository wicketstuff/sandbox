/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class PrintPlugin extends Plugin {

    private PluginButton printButton;

    public PrintPlugin() {
        super("print");
        printButton = new PluginButton("print", this);
    }

    public PluginButton getPrintButton() {
        return printButton;
    }
}
