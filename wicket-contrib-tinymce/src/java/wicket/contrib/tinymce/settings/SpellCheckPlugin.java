/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * This plugin adds spell checking feature.
 *
 * @author Iulian-Corneliu COSTAN
 */
public class SpellCheckPlugin extends Plugin {

    private PluginButton spellCheckButton;

    public SpellCheckPlugin() {
        super("iespell");
        spellCheckButton = new PluginButton("iespell", this);
    }

    public PluginButton getSpellCheckButton() {
        return spellCheckButton;
    }
}
