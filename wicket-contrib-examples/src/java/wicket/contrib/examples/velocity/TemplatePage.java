/*
 * $Id$ $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.examples.velocity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wicket.MarkupContainer;
import wicket.PageParameters;
import wicket.contrib.markup.html.velocity.VelocityPanel;
import wicket.examples.WicketExamplePage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.util.resource.StringBufferResourceStream;

/**
 * Template example page.
 * 
 * @author Eelco Hillenius
 */
public class TemplatePage extends WicketExamplePage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** context to be used by the template. */
	private final Model<Map<String, List<Person>>> templateContext;

	/** the current template contents. */
	private StringBufferResourceStream template = new StringBufferResourceStream();
	{
		template.append("<fieldset>\n");
		template.append(" <legend>persons</legend>\n");
		template.append("  <ul>\n");
		template.append("   #foreach( $person in $persons )\n");
		template.append("    <li>\n");
		template.append("     ${person.lastName},\n");
		template.append("     ${person.firstName}\n");
		template.append("    </li>\n");
		template.append("  #end\n");
		template.append(" </ul>\n");
		template.append("</fieldset>\n");
	}

	/**
	 * Constructor
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public TemplatePage(final PageParameters parameters)
	{
		Map<String, List<Person>> map = new HashMap<String, List<Person>>();
		map.put("persons", VelocityTemplateApplication.getPersons());
		templateContext = Model.valueOf(map);

		new TemplateForm(this, "templateForm");
		new VelocityPanel<String, List<Person>>(this, "templatePanel", template, templateContext);
		new FeedbackPanel(this, "feedback");
	}

	/**
	 * Gets the current template contents.
	 * 
	 * @return the current template contents
	 */
	public final String getTemplate()
	{
		return template.asString();
	}

	/**
	 * Sets the current template contents.
	 * 
	 * @param template
	 *            the current template contents
	 */
	public final void setTemplate(String template)
	{
		this.template.clear();
		this.template.append(template);
	}

	/**
	 * Form for changing the template contents.
	 */
	private final class TemplateForm extends Form
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param name
		 *            component name
		 */
		public TemplateForm(MarkupContainer parent, String name)
		{
			super(parent, name);
			new TextArea<String>(this, "templateInput", new PropertyModel<String>(
					TemplatePage.this, "template"));
		}
	}
}