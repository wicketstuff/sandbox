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
package wicket.extensions.markup.html.beanedit;


import wicket.ResourceReference;
import wicket.extensions.markup.html.datepicker.DatePicker;
import wicket.extensions.markup.html.datepicker.DatePickerSettings;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.CheckBox;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Fragment;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.PropertyModel;
import wicket.model.ResourceModel;

/**
 * Abstract Panel for generic bean displaying/ editing. It's here to provide the constructors,
 * but does nothing else.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public abstract class AbstractBeanPanel extends Panel
{
	/** boolean types. */
	protected static final Class[] BOOL_TYPES = new Class[] { Boolean.class, Boolean.TYPE };

	protected static final Class[] DATE_TYPES = new Class[] { java.util.Date.class, java.sql.Date.class };
	
	/** basic java types. */
	protected static final Class[] BASE_TYPES = new Class[] { String.class, Number.class, Integer.TYPE, Double.TYPE, Long.TYPE, Float.TYPE, Short.TYPE, Byte.TYPE };

	/**
	 * Construct.
	 * @param id component id
	 * @param beanModel model with the JavaBean to be edited or displayed
	 */
	public AbstractBeanPanel(String id, IModel beanModel)
	{
		super(id, beanModel);
		if (beanModel == null)
		{
			throw new NullPointerException("argument beanModel must not be null");
		}
	}

	/**
	 * Does isAssignableFrom check on given class array for given type.
	 * @param types array of types
	 * @param type type to check against
	 * @return true if one of the types matched
	 */
	protected boolean checkAssignableFrom(Class[] types, Class type)
	{
		int len = types.length;
		for (int i = 0; i < len; i++)
		{
			if (types[i].isAssignableFrom(type))
			{
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Gets the header panel of this editor.
	 * @param panelId id of panel; must be used for constructing any panel
	 * @param beanModel model with the JavaBean to be edited or displayed
	 * @return the header panel
	 */
	protected Fragment newHeader(String panelId, BeanModel beanModel)
	{
		return new DefaultHeaderFragment(panelId,beanModel);
	}

	/**
	 * Gets the editor for the given property.
	 * @param panelId id of panel; must be used for constructing any panel
	 * @param propertyMeta property descriptor
	 * @return the editor
	 */
	protected WebMarkupContainer newPropertyEditor(String panelId, IPropertyMeta propertyMeta, BeanModel beanModel)
	{
		//BeanPropertyEditor editor = findCustomEditor(panelId, propertyMeta);
		WebMarkupContainer editor;
		
		editor = newDefaultEditor(panelId, propertyMeta, beanModel);

		return editor;
	}

	

	/**
	 * Gets a default property editor panel.
	 * @param panelId component id
	 * @param propertyMeta property descriptor
	 * @return a property editor
	 */
	protected WebMarkupContainer newDefaultEditor(final String panelId, final IPropertyMeta propertyMeta, final BeanModel beanModel)
	{
		WebMarkupContainer editor;
		final Class type = propertyMeta.getType();
		if (checkAssignableFrom(BOOL_TYPES, type))
		{
			editor = new CheckFieldFragment(panelId, propertyMeta, beanModel);
		}
		else if (checkAssignableFrom(BASE_TYPES, type))
		{
			editor = new TextFieldFragment(panelId, propertyMeta, beanModel);
		}
		else if (checkAssignableFrom(DATE_TYPES, type))
		{
			editor = new DateFieldFragment(panelId, propertyMeta, beanModel);
		}
		else
		{
			return new ButtonToMoreDetails(panelId, propertyMeta, beanModel);
		}
		return editor;
	}


	/**
	 * Panel for an input field.
	 */
	final class TextFieldFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public TextFieldFragment(String id, final IPropertyMeta propertyMeta, final BeanModel beanModel)
		{
			super(id, "propertyInput");
			setRenderBodyOnly(true);
			Class type = propertyMeta.getType();
			IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());
			TextField field = new TextField("value", model, type);
			field.setEnabled( !propertyMeta.isReadOnly() );
			add(field);
		}
	}
	
	/**
	 * Panel for view field
	 */
	final class ViewFieldFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public ViewFieldFragment(String id, final IPropertyMeta propertyMeta, final BeanModel beanModel)
		{
			super(id, "propertyView");
			setRenderBodyOnly(true);
			IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());			
			Label field = new Label("value", model);
			add(field);
		}
	}
	
	final class DateFieldFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public DateFieldFragment(String id, final IPropertyMeta propertyMeta, final BeanModel beanModel)
		{
			super(id, "propertyDate");
			setRenderBodyOnly(true);
			Class type = propertyMeta.getType();
			IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());
			TextField field = new TextField("value", model, type);
			field.setEnabled( !propertyMeta.isReadOnly() );
			add(field);
			
			// .. and the date picker
			DatePickerSettings settings = new DatePickerSettings();
			settings.setStyle( settings.newStyleWinter() );
			settings.setIcon( new ResourceReference(DatePicker.class, "calendar_icon_2.gif") );
			add(new DatePicker( "datePicker", field, settings));	
			
		}
	}	

	/**
	 * Panel for a check box.
	 */
	final class CheckFieldFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public CheckFieldFragment(String id, final IPropertyMeta propertyMeta, final BeanModel beanModel)
		{
			super(id, "propertyCheck");
			setRenderBodyOnly(true);
			IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());
			CheckBox field = new CheckBox("value",model);
			field.setEnabled( !propertyMeta.isReadOnly() );
			add(field);
		}
	}

	/**
	 * Panel for a button to more details.
	 */
	static final class ButtonToMoreDetails extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public ButtonToMoreDetails(String id, final IPropertyMeta propertyMeta, final BeanModel beanModel)
		{
			super(id, "propertyButton");
			add(new Link("button")
			{
				private static final long serialVersionUID = 1L;

				public void onClick()
				{
				}
			});
		}
	}
	
	final class DefaultHeaderFragment extends Fragment {

		public DefaultHeaderFragment(String id, final BeanModel beanModel) {
			super(id, "defaultHeader");
			add(new Label("displayName", new ResourceModel(beanModel.getBean().getClass().getName()+".header", "")));
		} 
	}
	

	
}
