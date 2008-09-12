/*
 * Copyright 2008 by Pointbreak (Amsterdam, The Netherlands)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.InPlaceSaveBehavior;

/**
 * This plugin adds a save button that can be tied to the {@link InPlaceSaveBehavior}
 */
public class WicketSavePlugin extends Plugin {
    private PluginButton saveButton;
    private PluginButton cancelButton;
    private String saveCallbackname;
    private String cancelCallbackname;

    public WicketSavePlugin(InPlaceSaveBehavior behavior) {
        super("wicketsave");
        saveButton = new PluginButton("save", this);
        cancelButton = new PluginButton("cancel", this);
        saveCallbackname = behavior.getSaveCallbackName();
        cancelCallbackname = behavior.getCancelCallbackName();
    }

    public PluginButton getSaveButton() {
        return saveButton;
    }

    public PluginButton getCancelButton() {
        return cancelButton;
    }

    protected void definePluginSettings(StringBuffer buffer) {
        super.definePluginSettings(buffer);
        buffer.append(",\n\tsave_onwicketsavecallback: '" + saveCallbackname + "'");
        buffer.append(",\n\tsave_onwicketcancelcallback: '" + cancelCallbackname + "'");
    }
}
