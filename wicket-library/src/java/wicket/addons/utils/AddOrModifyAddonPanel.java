/*
 * $Id$
 * $Revision$ $Date$
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
package wicket.addons.utils;

import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.addons.ServiceLocator;
import wicket.addons.db.Addon;
import wicket.addons.db.Category;
import wicket.addons.services.AddonService;
import wicket.markup.html.border.Border;
import wicket.markup.html.form.ChoiceRenderer;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public abstract class AddOrModifyAddonPanel extends Border
{
	private final AddonSubmitForm form;

	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public AddOrModifyAddonPanel(final String id, Addon addon)
	{
		super(id);

		if (addon == null)
		{
			addon = Addon.Factory.newInstance();
			addon.setCategory(Category.Factory.newInstance());
		}

		// Create and add feedback panel to page
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);

		form = new AddonSubmitForm("form", addon);
		add(form);
	}

	/**
	 * @see wicket.MarkupContainer#add(wicket.Component)
	 */
	public MarkupContainer addToForm(Component child)
	{
		return form.add(child);
	}

	public abstract void onSubmit(final Addon addon);

	public final class AddonSubmitForm extends Form
	{
		final private Addon addon;
		private String otherLicense;
		private Category category;

		/**
		 * Constructor
		 * 
		 * @param componentName
		 *            Name of form
		 */
		public AddonSubmitForm(final String componentName, final Addon addon)
		{
			super(componentName);
			this.addon = addon;

			add(new TextField("name", new PropertyModel(addon, "name")));
			add(new TextArea("description", new PropertyModel(addon, "description")));
			add(new TextField("homepage", new PropertyModel(addon, "homepage")));
			add(new TextField("downloadUrl", new PropertyModel(addon, "downloadUrl")));
			add(new TextField("version", new PropertyModel(addon, "version")));
			add(new TextField("wicketVersion", new PropertyModel(addon, "wicketVersion")));
			add(new TextField("otherLicense", new PropertyModel(this, "otherLicense")));

			AddonService addonService = ServiceLocator.instance().getAddonService();
			final List licenseList = addonService.getAllLicenseNames();
			add(new DropDownChoice("license", new PropertyModel(addon, "license"), licenseList));

			final List categoryList = ServiceLocator.instance().getCategoryService()
					.getAllCategories();
			add(new CategoryDropDownChoice("category", addon, categoryList));
		}

		public void setOtherLicense(final String license)
		{
			this.otherLicense = license;
		}

		public String getOtherLicense()
		{
			return otherLicense;
		}

		/**
		 * Show the resulting valid edit
		 * 
		 * @param cycle
		 *            The request cycle
		 */
		public final void onSubmit()
		{
			if ((otherLicense != null) && (otherLicense.trim().length() > 0))
			{
				addon.setLicense(otherLicense);
			}

			AddOrModifyAddonPanel.this.onSubmit(addon);
		}
	}

	/**
	 * 
	 */
	private final class CategoryDropDownChoice extends DropDownChoice
	{
		/**
		 * Construct.
		 * 
		 * @param id
		 *            component id
		 */
		public CategoryDropDownChoice(final String id, final Addon addon, final List categories)
		{
			super(id);

			final Category category = addon.getCategory();
			if (category == null)
			{
				setModel(new Model(""));
			}
			else
			{
				setModel(new Model(category.getName()));
			}

			setChoices(categories);
			setChoiceRenderer(new ChoiceRenderer("name"));
		}
	}
}
