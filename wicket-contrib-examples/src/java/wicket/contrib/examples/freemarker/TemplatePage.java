/*
 * $Id$ $Revision$
 * $Date$
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
public class TemplatePage extends WicketExamplePage {
	
	/** Context to be used by the template */
	private final Model templateContext;

	/** The current template contents */
	private StringBufferResourceStream template = new StringBufferResourceStream(); {
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
	 * @param parameters Page parameters
	 */
	public TemplatePage(final PageParameters parameters) {
		HashMap map = new HashMap();
		map.put("people", FreeMarkerTemplateApplication.getPeople());
		templateContext = Model.valueOf(map);

		add(new TemplateForm("templateForm"));
		add(new FreeMarkerPanel("templatePanel", template, templateContext));
		add(new FeedbackPanel("feedback"));
	}

	/**
	 * Gets the current template contents.
	 * 
	 * @return the current template contents
	 */
	public final String getTemplate() {
		return template.asString();
	}

	/**
	 * Sets the current template contents.
	 * 
	 * @param templateContents the current template contents
	 */
	public final void setTemplate(String templateContents) {
		template.clear();
		template.append(templateContents);
	}

	/**
	 * Form for changing the template contents.
	 */
	private final class TemplateForm extends Form {
		
		private TextArea templateTextArea;
		
		/**
		 * Constructor.
		 * 
		 * @param name component name
		 */
		public TemplateForm(String name) {
			super(name);
			add(templateTextArea = new TextArea(
				"templateInput",
				new PropertyModel(new Model(TemplatePage.this), "template")));
		}

		/**
		 * @see wicket.markup.html.form.Form#onSubmit()
		 */
		protected void onSubmit() { }
	}
}
