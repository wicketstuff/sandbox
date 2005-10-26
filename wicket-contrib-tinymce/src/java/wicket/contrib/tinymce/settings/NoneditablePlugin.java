/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class NoneditablePlugin extends Plugin {

    public NoneditablePlugin() {
        super("noneditable");
    }

    public String defineProperties() {
        return null;
    }
}
