/*
 * $Id: AjaxAutocompleteTextField.java 612 2006-03-06 22:46:35 -0800 (Mon, 06
 * Mar 2006) eelco12 $ $Revision: 2011 $ $Date: 2006-03-06 22:46:35 -0800 (Mon,
 * 06 Mar 2006) $
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
package org.wicketstuff.scriptaculous.autocomplete;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.target.basic.StringRequestTarget;

/**
 * Ajax autocomplete behavior provides an ajax callback for populating results.
 * The ajax response is formatted as an unordered list of items.  ex:
 * <pre>
 *   &lt;ul&gt;
 *     &lt;li&gt;Red&lt;/li&gt;
 *     &lt;li&gt;Green&lt;/li&gt;
 *     &lt;li&gt;Blue&lt;/li&gt;
 *   &lt;/ul&gt;
 * </pre>
 *
 * For customizing the layout of the response, see {@link CustomLayoutAjaxAutocompleteTextField}
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AjaxAutocompleteBehavior extends AbstractAutocompleteBehavior
{
	private static final long serialVersionUID = 1L;

	protected String getAutocompleteType()
	{
		return "Ajax.Autocompleter";
	}

	protected String getThirdAutocompleteArgument()
	{
		return "'" + getCallbackUrl() + "'";
	}

	/**
	 * extension point to lookup results for user's input.
	 *
	 * @param input
	 * @return
	 */
	protected abstract String[] getResults(String input);

	public void onRequest() {
		FormComponent formComponent = (FormComponent)getComponent();

		formComponent.validate();
		if (formComponent.isValid())
		{
			formComponent.updateModel();
		}
		String input = formComponent.getValue();
		RequestCycle.get().setRequestTarget(new StringRequestTarget(formatResultsAsUnorderedList(getResults(input))));
	}

	private String formatResultsAsUnorderedList(String[] results)
	{
		StringBuffer s = new StringBuffer();
		s.append("<ul>\n");
		if (null != results) {
			for (int x = 0; x < results.length; x++) {
				String result = results[x];
				s.append("  <li>" + result + "</li>\n");
			}
		}
		s.append("</ul>\n");
		return s.toString();
	}
}
