/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.addons.utils;

import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.addons.hibernate.Addon;
import wicket.addons.hibernate.Category;
import wicket.addons.hibernate.IAddonDao;
import wicket.markup.html.border.Border;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.model.ChoiceList;
import wicket.markup.html.form.model.IChoice;
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
     * @param parameters
      */
    public AddOrModifyAddonPanel(final String id, final IAddonDao dao, final int addonId)
    {
        super(id);
        
        final Addon addon;
        if (addonId > 0)
        { 
            addon = (Addon) dao.load(Addon.class, new Integer(addonId));
        }
        else
        {
            addon = new Addon();
            addon.setCategory(new Category());
        }
        
        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);
        
        form = new AddonSubmitForm("form", feedback, dao, addon);
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
        final private IAddonDao dao;
        private String otherLicense;
        private Category category;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public AddonSubmitForm(final String componentName, final FeedbackPanel feedback, final IAddonDao dao, final Addon addon)
        {
            super(componentName, feedback);
            this.dao = dao;
            this.addon = addon;
            
            add(new TextField("name", new PropertyModel(addon, "name")));
            add(new TextArea("description", new PropertyModel(addon, "description")));
            add(new TextField("homepage", new PropertyModel(addon, "homepage")));
            add(new TextField("downloadUrl", new PropertyModel(addon, "downloadUrl")));
            add(new TextField("version", new PropertyModel(addon, "version")));
            add(new TextField("wicketVersion", new PropertyModel(addon, "wicketVersion")));
            add(new TextField("otherLicense", new PropertyModel(this, "otherLicense")));
            
            final List licenseList = dao.getLicenseNames();
            add(new DropDownChoice("license", new PropertyModel(addon, "license"), licenseList));
            
            final List categoryList = dao.getCategories();
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
         * @param cycle The request cycle
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
    
    private class FaultTolerantPropertyModel extends PropertyModel
    {
    	public FaultTolerantPropertyModel(final Object modelObject, final String expression)
    	{
    		super(modelObject, expression);
    	}
        
    	/**
		 * @see wicket.model.AbstractPropertyModel#onGetObject(wicket.Component)
		 */
		protected Object onGetObject(Component component)
		{
		    try
		    {
		        return super.onGetObject(component);
		    }
		    catch (RuntimeException ex)
		    {
		        // ignore exception
		    }
		    
		    return null;
		}
		
		protected void onSetObject(final Component component, Object object)
		{
		    super.onSetObject(component, object);
		}
    }

	/**
	 * 
	 */
	private final class CategoryDropDownChoice extends DropDownChoice
	{
		/**
		 * Construct.
		 * @param id component id
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
            
			// use a custom implementation of choices, as we want to display
			// the choices localized
			ChoiceList categoriesChoiceList = new ChoiceList(categories)
			{
				protected IChoice newChoice(Object object, int index)
				{
					return new CategoryChoice((Category)object, index);
				}
			};
			setChoices(categoriesChoiceList);
		}
	}

	/**
	 * Choice for a locale.
	 */
	private final class CategoryChoice implements IChoice
	{
		/** The index of the choice. */
		private final int index;

		/** The choice model object. */
		private final Category category;

		/**
		 * Constructor.
		 * @param locale The locale
		 * @param index The index of the object in the choice list
		 */
		public CategoryChoice(final Category category, final int index)
		{
			this.category = category;
			this.index = index;
		}

		/**
		 * @see wicket.markup.html.form.model.IChoice#getDisplayValue()
		 */
		public String getDisplayValue()
		{
			return category.getName();
		}

		/**
		 * @see wicket.markup.html.form.model.IChoice#getId()
		 */
		public String getId()
		{
			return Integer.toString(this.index);
		}

		/**
		 * @see wicket.markup.html.form.model.IChoice#getObject()
		 */
		public Object getObject()
		{
			return category;
		}
	}
}
