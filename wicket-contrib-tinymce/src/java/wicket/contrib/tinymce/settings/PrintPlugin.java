/*
 *  Copyright (C) 2005  Iulian-Corneliu Costan
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.settings.Plugin;
import wicket.contrib.tinymce.settings.PluginButton;

/**
 * This plugin adds a print button to TinyMCE.
 *
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
