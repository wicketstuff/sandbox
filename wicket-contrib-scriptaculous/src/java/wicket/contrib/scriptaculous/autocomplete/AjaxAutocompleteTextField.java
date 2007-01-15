/*
 * $Id: AjaxAutocompleteTextField.java 612 2006-03-06 22:46:35 -0800 (Mon, 06
 * Mar 2006) eelco12 $ $Revision$ $Date: 2006-03-06 22:46:35 -0800 (Mon,
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
package wicket.contrib.scriptaculous.autocomplete;

import java.util.Collection;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.ScriptaculousAjaxBehavior;
import wicket.markup.html.form.FormComponent;
import wicket.util.resource.StringBufferResourceStream;

/**
 * Ajax Autocomplete textfield provides an ajax callback for populating results.
 * 
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AjaxAutocompleteTextField<T> extends AutocompleteTextFieldSupport<T>
{
	private final ScriptaculousAjaxBehavior callbackBehavior;

	public AjaxAutocompleteTextField(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.callbackBehavior = new ScriptaculousAjaxBehavior()
		{
			private static final long serialVersionUID = 1L;

			public void onRequest()
			{
				FormComponent formComponent = (FormComponent)getComponent();

				formComponent.validate();
				if (formComponent.isValid())
				{
					formComponent.updateModel();
				}
				String input = formComponent.getValue();
				getResponse().write(formatResultsAsUnorderedList(getResults(input)));
			}
		};
		add(callbackBehavior);
	}

	private String formatResultsAsUnorderedList(Collection<String> results)
	{
		StringBufferResourceStream s = new StringBufferResourceStream();
		s.append("<ul>\n");
		for (String result : results)
		{
			s.append("  <li>" + result + "</li>\n");
		}
		s.append("</ul>\n");
		return s.toString();
	}


	@Override
	protected String getAutocompleteType()
	{
		return "Ajax.Autocompleter";
	}

	@Override
	protected String getThirdAutocompleteArgument()
	{
		return "" + callbackBehavior.getCallbackUrl();
	}

	/**
	 * extension point to lookup results for user's input.
	 * 
	 * @param input
	 * @return
	 */
	protected abstract Collection<String> getResults(String input);

}
