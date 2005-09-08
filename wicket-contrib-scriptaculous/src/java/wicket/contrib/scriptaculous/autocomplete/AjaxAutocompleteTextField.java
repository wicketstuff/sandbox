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

import wicket.ResourceReference;
import wicket.markup.ComponentTag;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.markup.html.PackageResourceReference;
import wicket.markup.html.ajax.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextField;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

/**
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AjaxAutocompleteTextField extends TextField {
    private final AutocompleteEventHandler handler;

    public AjaxAutocompleteTextField(String id) {
        super(id);
        handler = new AutocompleteEventHandler();
        add(handler);
    }

    protected abstract String[] getResults(String input);

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", getId());
        tag.put("autocomplete", "off");
    }

    protected void onRender() {
        super.onRender();

        String autocompleteId = getId() + "_autocomplete";
        getResponse().write("<div class=\"auto_complete\" id=\"" + autocompleteId  + "\"></div>");
        getResponse().write("<script type=\"text/javascript\">new Ajax.Autocompleter('" + getId() + "', '" + autocompleteId + "', '" + handler.getCallbackUrl() + "', {})</script>");
    }

    protected ResourceReference getCss() {
    	return new PackageResourceReference(AutocompleteTextField.class, "style.css");
    }

    private class AutocompleteEventHandler extends ScriptaculousAjaxHandler {
        public final void renderHeadContribution(HtmlHeaderContainer container) {
        	addCssReference(container, getCss());
        }

        private void addCssReference(HtmlHeaderContainer container, ResourceReference ref) {
            String url = container.getPage().urlFor(ref.getPath());
            String s =
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + url + "\"/>\n";
            write(container, s);
        }

        /**
         * Writes the given string to the header container.
         * @param container the header container
         * @param s the string to write
         */
        private void write(HtmlHeaderContainer container, String s) {
            container.getResponse().write(s);
        }

        /**
         */
        public void onComponentTag(ComponentTag tag) {
        }

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
