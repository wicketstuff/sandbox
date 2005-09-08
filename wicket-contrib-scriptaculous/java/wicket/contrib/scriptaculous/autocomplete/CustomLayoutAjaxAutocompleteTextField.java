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

import wicket.PageParameters;
import wicket.ResourceReference;
import wicket.markup.ComponentTag;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.markup.html.PackageResourceReference;
import wicket.markup.html.ajax.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.html.form.TextField;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

/**
 * Autocomplete text field that allows for customized layout of autocomplete entries.
 * A user defined <code>PageContribution</code> is used to display the autocomplete
 * information.
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class CustomLayoutAjaxAutocompleteTextField extends TextField {
    private final Class page;

	public CustomLayoutAjaxAutocompleteTextField(String id, Class page) {
        super(id);
		this.page = page;

        add(new AutocompleteEventHandler());
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", getId());
        tag.put("autocomplete", "off");
    }

    protected void onRender() {
        super.onRender();

        PageParameters parameters = new PageParameters();
        parameters.put("fieldName", this.getId());
        String url = this.getPage().urlFor(null, page, parameters);
        String autocompleteId = getId() + "_autocomplete";
        getResponse().write("<div class=\"auto_complete\" id=\"" + autocompleteId  + "\"></div>");
        getResponse().write("<script type=\"text/javascript\">new Ajax.Autocompleter('" + getId() + "', '" + autocompleteId + "', '" + url + "', {})</script>");
    }

    protected ResourceReference getCss() {
    	return new PackageResourceReference(CustomLayoutAjaxAutocompleteTextField.class, "style.css");
    }


    public void renderHead(HtmlHeaderContainer container) {
		super.renderHead(container);

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


    private class AutocompleteEventHandler extends ScriptaculousAjaxHandler {
        protected IResourceStream getResponse() {
            StringBufferResourceStream s = new StringBufferResourceStream();
            return s;
        }
    }
}
