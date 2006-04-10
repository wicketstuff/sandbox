/*
 * $Id$ $Revision:
 * 408 $ $Date$
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
package wicket.addons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.utils.GmailClient;
import wicket.markup.html.form.Form;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * @author Juergen Donnerstag
 */
public final class CategoryRequest extends BaseHtmlPage /* AuthenticateHtmlPage */
{
	private static final Log log = LogFactory.getLog(CategoryRequest.class);

	private final GmailClient gmailClient;
	private final SimpleMailMessage defaultMessage;

	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public CategoryRequest(final PageParameters parameters)
	{
		super(parameters, "Wicket-Addons: Category Request Form");

		BeanFactory fac = ((AddonApplication)RequestCycle.get().getApplication()).getBeanFactory();
		this.gmailClient = new GmailClient((MailSender)fac.getBean("mailSender"));
		this.defaultMessage = (SimpleMailMessage)fac.getBean("mailMessage");

		// Create and add feedback panel to page
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);

		add(new CategoryRequestForm("form"));
	}

	public final class CategoryRequestForm extends Form
	{
		/**
		 * Constructor
		 * 
		 * @param componentName
		 *            Name of form
		 */
		public CategoryRequestForm(final String componentName)
		{
			super(componentName);
		}

		/**
		 * Show the resulting valid edit
		 * 
		 * @param cycle
		 *            The request cycle
		 */
		public final void onSubmit()
		{
			sendMail();

			final RequestCycle cycle = getRequestCycle();
			cycle.setResponsePage(new CategoryRequestStored());
		}

		private void sendMail()
		{
			// Create a threadsafe "sandbox" of the message
			SimpleMailMessage msg = new SimpleMailMessage(defaultMessage);
			msg.setTo("juergen.donnerstag@gmail.com");
			msg.setText("Dies ist ein Test!");

			gmailClient.sendMail(msg);
		}
	}

}
