/*
 * $Id: AutocompleteTextField.java 528 2006-01-08 04:14:46 -0800 (Sun, 08 Jan
 * 2006) jdonnerstag $ $Revision$ $Date: 2006-01-08 04:14:46 -0800 (Sun,
 * 08 Jan 2006) $
 *
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.scriptaculous.autocomplete;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.markup.ComponentTag;
import wicket.markup.html.internal.HtmlHeaderContainer;

/**
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class AutocompleteTextField<T> extends AutocompleteTextFieldSupport<T>
{
	private static final long serialVersionUID = 1L;
	private final String[] results;

	public AutocompleteTextField(MarkupContainer parent, String id, String[] results)
	{
		super(parent, id);

		this.results = results;
	}

	public void renderHead(HtmlHeaderContainer container)
	{
		super.renderHead(container);

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Autocompleter.Local(");
		builder.addLine("  '" + getId() + "', ");
		builder.addLine("  '" + getAutocompleteId() + "', ");
		builder.addLine("  " + buildResults() + ", {} );");
		container.getResponse().write(builder.buildScriptTagString());
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("id", getId());
		tag.put("autocomplete", "off");
	}

	private String buildResults()
	{
		String result = "new Array(";
		for (int x = 0; x < results.length; x++)
		{
			result += "\"" + results[x] + "\"";
			if (x < results.length - 1)
			{
				result += ",";
			}
		}
		result += ")";
		return result;
	}
}
