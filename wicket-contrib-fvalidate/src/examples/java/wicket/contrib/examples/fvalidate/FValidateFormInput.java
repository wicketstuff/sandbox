/*
 * $Id$
 * $Revision$
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
package wicket.contrib.examples.fvalidate;

import java.util.Date;
import java.util.Locale;

import wicket.IFeedback;
import wicket.PageParameters;
import wicket.Session;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.markup.html.form.fvalidate.FValidateTextField;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.validation.RequiredValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;

/**
 * Example for form input.
 * 
 * @author Eelco Hillenius
 */
public class FValidateFormInput extends WicketExamplePage
{
	/**
	 * Constructor
	 * @param parameters Page parameters
	 */
	public FValidateFormInput(final PageParameters parameters)
	{
		Session.get().setLocale(Locale.ENGLISH);
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		add(new InputForm("inputForm", feedback));
	}

	/** Form for input. */
	private static class InputForm extends Form
	{
		/**
		 * Construct.
		 * @param name componentnaam
		 * @param feedback error handler
		 */
		public InputForm(String name, IFeedback feedback)
		{
			super(name, new CompoundPropertyModel(new TestInputObject()), feedback);

			FValidateTextField stringInput = new FValidateTextField("stringProperty");
			stringInput.add(RequiredValidator.getInstance());
			FValidateTextField integerInput = new FValidateTextField("integerProperty", Integer.class);
			integerInput.add(RequiredValidator.getInstance());
			FValidateTextField doubleInput = new FValidateTextField("doubleProperty", Double.class);
			doubleInput.add(RequiredValidator.getInstance());
			FValidateTextField dateInput = new FValidateTextField("dateProperty", Date.class);
			dateInput.add(RequiredValidator.getInstance());
			add(stringInput);
			add(integerInput);
			add(doubleInput);
			add(dateInput);
		}

		/**
		 * @see wicket.markup.html.form.Form#onSubmit()
		 */
		public void onSubmit()
		{
			// Form validation successful. Display message showing edited model.
			info("Saved model " + getRootModelObject());
		}
	}
}