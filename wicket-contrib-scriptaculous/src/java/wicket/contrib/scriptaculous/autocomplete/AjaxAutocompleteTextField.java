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
package wicket.contrib.scriptaculous.autocomplete;

import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

/**
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AjaxAutocompleteTextField extends AutocompleteTextFieldSupport {
    private final AutocompleteEventHandler handler;

    public AjaxAutocompleteTextField(String id) {
        super(id);
        handler = new AutocompleteEventHandler();
        add(handler);
    }

    protected abstract String[] getResults(String input);

    /**
     * @see wicket.Component#renderHead(wicket.markup.html.internal.HtmlHeaderContainer)
     */
    public void renderHead(HtmlHeaderContainer container) {
        super.renderHead(container);

        container.getResponse().write("<script type=\"text/javascript\">\n" +
                        "var myrules = { \n" +
                        "\t'#" + getId() + "' : function(el){ \n" +
                        "\t\tnew Ajax.Autocompleter('" + getId() + "', '" + getAutocompleteId() + "', '" + handler.getCallbackUrl() + "', {});\n" +
                        "\t} \n" +
                        "} \n" +
                        "Behaviour.register(myrules);\n" +
                        "</script>\n");
    }

    private class AutocompleteEventHandler extends ScriptaculousAjaxHandler {

        protected IResourceStream getResponse() {
            StringBufferResourceStream s = new StringBufferResourceStream();
            FormComponent formComponent = (FormComponent) getComponent();

            formComponent.validate();
            if (formComponent.isValid()) {
                formComponent.updateModel();
            }
            String value = formComponent.getValue();

            s.append("<ul>\n");
            String[] results = getResults(value);
            for (int x = 0; x < results.length; x++) {
                String result = results[x];
                s.append("<li>" + result + "</li>\n");
            }
            s.append("</ul>\n");

            return s;
        }
    }
}
