/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * This is the "Insert Flash" Dialog, it enables users to insert flash movies into TinyMCE.
 * <p/>
 * You can control the wmode, quality and menu by setting the flash_wmode, flash_quality and flash_menu options
 *
 * @author Iulian-Corneliu COSTAN
 */
public class FlashPlugin extends Plugin {

    private PluginButton flashButton;

    public FlashPlugin() {
        super("flash");
        flashButton = new PluginButton("flash", this);
    }

    public PluginButton getFlashButton() {
        return flashButton;
    }

    public String defineProperties() {
        return null;
    }

    public String getExtension() {
        return null;
    }
}
