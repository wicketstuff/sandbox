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

import java.io.Serializable;

import wicket.ResourceReference;
import wicket.extensions.markup.html.datepicker.DatePicker;
import wicket.extensions.markup.html.datepicker.DatePickerSettings;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.CheckBox;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Fragment;
import wicket.model.IModel;
import wicket.model.PropertyModel;

/**
 * Panel for generic bean displaying/ editing.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public class BeanPanel extends AbstractBeanPanel
{
	private static final long serialVersionUID = 1L;


	private BeanModel beanModel;

	/**
	 * Construct.
	 * @param id component id
	 * @param bean JavaBean to be edited or displayed
	 */
	public BeanPanel(String id, Serializable bean)
	{
		this(id, new BeanModel(bean));
	}

	/**
	 * Construct.
	 * @param id component id
	 * @param beanModel model with the JavaBean to be edited or displayed
	 */
	public BeanPanel(String id, BeanModel beanModel)
	{
		super(id, beanModel);
		this.beanModel = beanModel;
		setRenderBodyOnly(true);
		/*
		 * Add the bean panel table header
		 */
		Fragment header = newHeader("header", beanModel);
		if (header == null)
		{
			throw new NullPointerException("header must be not null");
		}
		add( header );
		
		/*
		 * Add the properties
		 */
		add( new ListView("propertiesList", beanModel.getPropertiesList() ) {
			
			protected void populateItem(ListItem item) 
			{
				IPropertyMeta propertyMeta = (IPropertyMeta)item.getModelObject();
				item.add(new Label("displayName", propertyMeta.getLabel()));
				WebMarkupContainer propertyEditor = newPropertyEditor("editor", propertyMeta);
				if (propertyEditor == null)
				{
					throw new NullPointerException("propertyEditor must be not null");
				}
				item.add(propertyEditor);
				
			} } );
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
	protected WebMarkupContainer newPropertyEditor(String panelId, IPropertyMeta propertyMeta)
	{
		//BeanPropertyEditor editor = findCustomEditor(panelId, propertyMeta);
		WebMarkupContainer editor;
		
		editor = newDefaultEditor(panelId, propertyMeta);

		return editor;
	}

	

	/**
	 * Gets a default property editor panel.
	 * @param panelId component id
	 * @param propertyMeta property descriptor
	 * @return a property editor
	 */
	protected final WebMarkupContainer newDefaultEditor(final String panelId, final IPropertyMeta propertyMeta)
	{
		WebMarkupContainer editor;
		final Class type = propertyMeta.getType();
		if (checkAssignableFrom(BOOL_TYPES, type))
		{
			editor = new CheckFieldFragment(panelId, propertyMeta);
		}
		else if (checkAssignableFrom(BASE_TYPES, type))
		{
			editor = new TextFieldFragment(panelId, propertyMeta);
		}
		else if (checkAssignableFrom(DATE_TYPES, type))
		{
			editor = new DateFieldFragment(panelId, propertyMeta);
		}
		else
		{
			return new ButtonToMoreDetails(panelId, propertyMeta);
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
		public TextFieldFragment(String id, final IPropertyMeta propertyMeta)
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
	
	final class DateFieldFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public DateFieldFragment(String id, final IPropertyMeta propertyMeta)
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
		public CheckFieldFragment(String id, final IPropertyMeta propertyMeta)
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
		public ButtonToMoreDetails(String id, final IPropertyMeta propertyMeta)
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

		public DefaultHeaderFragment(String id, BeanModel beanModel) {
			super(id, "defaultHeader");
			add(new Label("displayName", beanModel.getBean().getClass().getName()));
		} 
		
	}
	

//	final class BeanNameModel extends LoadableDetachableModel {
//
//		protected Object load() {
//			BeanInfo beanInfo = beanModel.getBeanInfo();
//
//			if (beanInfo != null)
//			{
//				BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
//				String displayName;
//
//				if (beanDescriptor != null)
//				{
//					displayName = beanDescriptor.getDisplayName();
//				}
//				else
//				{
//					Class clazz = beanModel.getBean().getClass();
//					displayName = (clazz != null) ? clazz.getName() : null;
//				}
//				return displayName;
//			}
//
//			return null;
//		} 
//		
//	}
}
