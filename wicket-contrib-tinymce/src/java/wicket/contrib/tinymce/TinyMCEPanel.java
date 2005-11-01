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
package wicket.contrib.tinymce;

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.markup.html.panel.Panel;
import wicket.markup.html.resources.JavaScriptReference;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEPanel extends Panel {

    private TinyMCESettings settings;

    public TinyMCEPanel(final String id) {
        this(id, new TinyMCESettings());
    }

    public TinyMCEPanel(final String id, final TinyMCESettings settings) {
        super(id);

        add(new JavaScriptReference("tinymce", TinyMCEPanel.class, "tiny_mce/tiny_mce_src.js"));
        add(new WebComponent("initScript") {

            protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                String body = "\ntinyMCE.init({" + settings.toJavaScript() + "\n});\n";
                replaceComponentTagBody(markupStream, openTag, body);
            }
        });
    }
}
