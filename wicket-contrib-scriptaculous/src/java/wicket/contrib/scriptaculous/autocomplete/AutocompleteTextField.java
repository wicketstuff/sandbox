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
import wicket.markup.ComponentTag;

/**
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class AutocompleteTextField extends AutocompleteTextFieldSupport {

	private final String[] results;

	public AutocompleteTextField(String id, String[] results) {
        super(id);

		this.results = results;
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("id", getId());
        tag.put("autocomplete", "off");
    }

    protected void onRender() {
        super.onRender();

        getResponse().write("<script type=\"text/javascript\">new Autocompleter.Local('" + getId() + "', '" + getAutocompleteId() + "', " + buildResults() + ", {})</script>");
    }

    private String buildResults() {
    	String result = "new Array(";
    	for (int x = 0; x < results.length; x++) {
    		result += "\"" + results[x] + "\"";
    		if (x < results.length - 1) {
    			result += ",";
    		}
    	}
    	result += ")";
    	return result;
	}
}
