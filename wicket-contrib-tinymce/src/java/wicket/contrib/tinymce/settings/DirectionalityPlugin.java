/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class DirectionalityPlugin extends Plugin {

    private PluginButton ltrButton;
    private PluginButton rtlButton;

    public DirectionalityPlugin() {
        super("directionality");
        rtlButton = new PluginButton("rtl", this);
        ltrButton = new PluginButton("ltr", this);
    }

    public PluginButton getLtrButton() {
        return ltrButton;
    }

    public PluginButton getRtlButton() {
        return rtlButton;
    }
}
