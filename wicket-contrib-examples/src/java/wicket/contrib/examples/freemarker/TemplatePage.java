/*
 * $Id: TemplatePage.java 526 2006-01-08 11:21:16 +0000 (Sun, 08 Jan 2006)
 * jdonnerstag $ $Revision$ $Date: 2006-01-08 11:21:16 +0000 (Sun, 08 Jan
 * 2006) $
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
package wicket.contrib.examples.freemarker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wicket.MarkupContainer;
import wicket.PageParameters;
import wicket.contrib.markup.html.freemarker.FreeMarkerPanel;
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
 * @author Jonas Klingstedt
 */
public class TemplatePage extends WicketExamplePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Context to be used by the template */
	private final Model<Map<String, List<Person>>> templateContext;

	/** The current template contents */
	private StringBufferResourceStream template = new StringBufferResourceStream();
	{
		template.append("<fieldset>\n");
		template.append(" <legend>persons</legend>\n");
		template.append("  <ul>\n");
		template.append("   <#list people as person>\n");
		template.append("    <li>\n");
		template.append("     ${person.lastName},\n");
		template.append("     ${person.firstName}\n");
		template.append("    </li>\n");
		template.append("   </#list>\n");
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
		map.put("people", FreeMarkerTemplateApplication.getPeople());
		templateContext = Model.valueOf(map);

		new TemplateForm(this, "templateForm");
		new FreeMarkerPanel<String, List<Person>>(this, "templatePanel", template, templateContext);
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
	 * @param templateContents
	 *            the current template contents
	 */
	public final void setTemplate(String templateContents)
	{
		template.clear();
		template.append(templateContents);
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
		 * Constructor.
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
