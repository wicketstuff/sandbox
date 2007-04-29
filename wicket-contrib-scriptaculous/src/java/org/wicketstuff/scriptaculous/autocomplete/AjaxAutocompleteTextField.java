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

import java.util.Collection;
import java.util.Iterator;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringBufferResourceStream;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;


/**
 * Ajax Autocomplete textfield provides an ajax callback for populating results.
 * 
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AjaxAutocompleteTextField extends AutocompleteTextFieldSupport
{
	private final ScriptaculousAjaxBehavior callbackBehavior;

	public AjaxAutocompleteTextField(String id)
	{
		super(id);
		this.callbackBehavior = new AjaxAutocompleteBehavior();
		add(callbackBehavior);
	}

	protected String getAutocompleteType()
	{
		return "Ajax.Autocompleter";
	}

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
	protected abstract Collection getResults(String input);
	
	private class AjaxAutocompleteBehavior extends ScriptaculousAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		protected IResourceStream getResponse() {
			FormComponent formComponent = (FormComponent)getComponent();

			formComponent.validate();
			if (formComponent.isValid())
			{
				formComponent.updateModel();
			}
			String input = formComponent.getValue();
			return formatResultsAsUnorderedList(getResults(input));
		}

		private IResourceStream formatResultsAsUnorderedList(Collection results)
		{
			StringBufferResourceStream s = new StringBufferResourceStream();
			s.append("<ul>\n");
			for (Iterator iter = results.iterator(); iter.hasNext();) {
				String result = (String) iter.next();
				s.append("  <li>" + result + "</li>\n");
			}
			s.append("</ul>\n");
			return s;
		}
	}
}
