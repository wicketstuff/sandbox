/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * The emotions plugin is able to insert smiley images into the TinyMCE editable area.
 *
 * @author Iulian-Corneliu COSTAN
 */
public class EmotionsPlugin extends Plugin {

    private PluginButton emotionsButton;

    public EmotionsPlugin() {
        super("emotions");
        emotionsButton = new PluginButton("emotions", this);
    }

    public PluginButton getEmotionsButton() {
        return emotionsButton;
    }
}
