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

import wicket.Component;
import wicket.MarkupContainer;
import wicket.RequestCycle;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.util.resource.StringBufferResourceStream;

/**
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AjaxAutocompleteTextField<T> extends AutocompleteTextFieldSupport<T>
{
	private class AutocompleteEventHandler extends ScriptaculousAjaxHandler
	{
		private static final long serialVersionUID = 1L;

		public void onRequest(Component component)
		{
			StringBufferResourceStream s = new StringBufferResourceStream();
			FormComponent formComponent = (FormComponent)getComponent();

			formComponent.validate();
			if (formComponent.isValid())
			{
				formComponent.updateModel();
			}
			String value = formComponent.getValue();

			s.append("<ul>\n");
			String[] results = getResults(value);
			for (int x = 0; x < results.length; x++)
			{
				String result = results[x];
				s.append("<li>" + result + "</li>\n");
			}
			s.append("</ul>\n");

			RequestCycle.get().getResponse().write(s.toString());
		}
	}

	private final AutocompleteEventHandler handler;

	public AjaxAutocompleteTextField(MarkupContainer parent, String id)
	{
		super(parent, id);
		handler = new AutocompleteEventHandler();
		add(handler);
	}

	/**
	 * @see wicket.Component#renderHead(wicket.markup.html.internal.HtmlHeaderContainer)
	 */
	public void renderHead(HtmlHeaderContainer container)
	{
		super.renderHead(container);

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Ajax.Autocompleter(");
		builder.addLine("  '" + getId() + "', ");
		builder.addLine("  '" + getAutocompleteId() + "', ");
		builder.addLine("  '" + handler.getCallbackUrl() + "', {} );");
		container.getResponse().write(builder.buildScriptTagString());
	}

	protected abstract String[] getResults(String input);
}
