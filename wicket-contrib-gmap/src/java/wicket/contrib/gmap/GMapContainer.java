/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.gmap;

import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebMarkupContainer;


/**
 * @author Iulian-Corneliu COSTAN
 */
class GMapContainer extends WebMarkupContainer {

    private GMap gmap;

    GMapContainer(final String id, GMap gmap) {
        super(id);
        this.gmap = gmap;
    }

    protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        String body = "\n//<![CDATA[\n" +
                "\n" +
                "function initGMap() {\n" +
                "if (GBrowserIsCompatible()) {\n" +
                "\n" + gmap.toJavaScript() + "\n" +
                "}\n" +
                "}\n" +
                "\n" +
                "//]]>\n";
        replaceComponentTagBody(markupStream, openTag, body);
    }
}
