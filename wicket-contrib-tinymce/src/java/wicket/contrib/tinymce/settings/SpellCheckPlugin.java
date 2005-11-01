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
