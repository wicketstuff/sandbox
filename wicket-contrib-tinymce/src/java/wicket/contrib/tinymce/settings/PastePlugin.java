/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class PastePlugin extends Plugin {

    private PluginButton pasteButton;
    private PluginButton pasteWordButton;
    private PluginButton pasteTextButton;

    public PastePlugin() {
        super("paste");
        pasteButton = new PluginButton("paste", this);
        pasteWordButton = new PluginButton("pasteword", this);
        pasteTextButton = new PluginButton("pastetext", this);
    }

    public PluginButton getPasteButton() {
        return pasteButton;
    }

    public PluginButton getPasteWordButton() {
        return pasteWordButton;
    }

    public PluginButton getPasteTextButton() {
        return pasteTextButton;
    }
}
