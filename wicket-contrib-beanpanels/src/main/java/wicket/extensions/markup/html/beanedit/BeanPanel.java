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

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import wicket.ResourceReference;
import wicket.WicketRuntimeException;
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
import wicket.model.LoadableDetachableModel;

/**
 * Panel for generic bean displaying/ editing.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public class BeanPanel extends AbstractBeanPanel
{
	private static final long serialVersionUID = 1L;


	/** boolean types. */
	private static final Class[] BOOL_TYPES = new Class[] { Boolean.class, Boolean.TYPE };

	private static final Class[] DATE_TYPES = new Class[] { java.util.Date.class, java.sql.Date.class };
	
	/** basic java types. */
	private static final Class[] BASE_TYPES = new Class[] { String.class, Number.class, Integer.TYPE, Double.TYPE, Long.TYPE, Float.TYPE, Short.TYPE, Byte.TYPE };

	/** edit mode. */
	private EditMode editMode = EditMode.READ_WRITE;
	
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
		add( new ListView("propertiesList", new BeanPropertiesListModel()) {
			
			protected void populateItem(ListItem item) 
			{
				PropertyMeta propertyMeta = (PropertyMeta)item.getModelObject();
				item.add(new Label("displayName", propertyMeta.getDisplayName()));
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
	protected WebMarkupContainer newPropertyEditor(String panelId, PropertyMeta propertyMeta)
	{
		//BeanPropertyEditor editor = findCustomEditor(panelId, propertyMeta);
		WebMarkupContainer editor;
		
		if (propertyMeta.getPropertyDescriptor() instanceof IndexedPropertyDescriptor)
		{
			throw new WicketRuntimeException("indexed properties not supported yet ");
		}
		else
		{
			editor = newDefaultEditor(panelId, propertyMeta);
		}

		return editor;
	}

	

	/**
	 * Gets the editMode.
	 * @return editMode
	 */
	protected EditMode getEditMode()
	{
		return editMode;
	}

	/**
	 * Sets the editMode.
	 * @param editMode editMode
	 */
	protected void setEditMode(EditMode editMode)
	{
		this.editMode = editMode;
	}

	/**
	 * Gets a default property editor panel.
	 * @param panelId component id
	 * @param propertyMeta property descriptor
	 * @return a property editor
	 */
	protected final WebMarkupContainer newDefaultEditor(final String panelId, final PropertyMeta propertyMeta)
	{
		WebMarkupContainer editor;
		final Class type = propertyMeta.getPropertyType();
		if (checkAssignableFrom(BOOL_TYPES, type))
		{
			editor = new PropertyCheckBox(panelId, propertyMeta);
		}
		else if (checkAssignableFrom(BASE_TYPES, type))
		{
			editor = new PropertyInput(panelId, propertyMeta);
		}
		else if (checkAssignableFrom(DATE_TYPES, type))
		{
			editor = new PropertyDate(panelId, propertyMeta);
		}
		else
		{
			return new ButtonToMoreDetails(panelId, propertyMeta);
		}
		return editor;
	}

	/**
	 * Does isAssignableFrom check on given class array for given type.
	 * @param types array of types
	 * @param type type to check against
	 * @return true if one of the types matched
	 */
	private boolean checkAssignableFrom(Class[] types, Class type)
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
	 * Finds a possible custom editor by looking for the type name + 'Editor'
	 * (e.g. mypackage.MyBean has editor mypackage.MyBeanEditor).
	 * @param panelId id of panel; must be used for constructing any panel
	 * @param propertyMeta property descriptor
	 * @return PropertyEditor if found or null
	protected final BeanPropertyEditor findCustomEditor(String panelId, PropertyMeta propertyMeta)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = getClass().getClassLoader();
		}

		Class type = propertyMeta.getPropertyType();
		String editorTypeName = type.getName() + "Editor";
		try
		{
			Class editorClass = classLoader.loadClass(editorTypeName);
			try
			{
				// get constructor
				Constructor constructor = editorClass.getConstructor(
						new Class[]{String.class, BeanModel.class, PropertyMeta.class, EditMode.class});

				// construct arguments
				Object[] args = new Object[]{panelId, BeanPanel.this.getModel(), propertyMeta, getEditMode()};

				// create editor instance
				BeanPropertyEditor editor = (BeanPropertyEditor)constructor.newInstance(args);
				return editor;
			}
			catch (SecurityException e)
			{
				throw new WicketRuntimeException(e);
			}
			catch (NoSuchMethodException e)
			{
				throw new WicketRuntimeException(e);
			}
			catch (InstantiationException e)
			{
				throw new WicketRuntimeException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new WicketRuntimeException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new WicketRuntimeException(e);
			}
		}
		catch(ClassNotFoundException e)
		{
			// ignore; there just is no custom editor
		}

		return null;
	}
	 */


	/**
	 * Panel for an input field.
	 */
	static final class PropertyInput extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public PropertyInput(String id, final PropertyMeta propertyMeta)
		{
			super(id, "propertyInput");
			setRenderBodyOnly(true);
			Class type = propertyMeta.getPropertyType();
			TextField field = new TextField("value", new BeanPropertyModel(propertyMeta), type);
			field.setEnabled( EditMode.READ_WRITE.equals(propertyMeta.getEditMode()) );
			add(field);
		}
	}
	
	static final class PropertyDate extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public PropertyDate(String id, final PropertyMeta propertyMeta)
		{
			super(id, "propertyDate");
			setRenderBodyOnly(true);
			Class type = propertyMeta.getPropertyType();
			TextField field = new TextField("value", new BeanPropertyModel(propertyMeta), type);
			field.setEnabled( EditMode.READ_WRITE.equals(propertyMeta.getEditMode()) );
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
	static final class PropertyCheckBox extends Fragment
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * @param id component id
		 * @param propertyMeta property descriptor
		 */
		public PropertyCheckBox(String id, final PropertyMeta propertyMeta)
		{
			super(id, "propertyCheck");
			setRenderBodyOnly(true);
			CheckBox field = new CheckBox("value", new BeanPropertyModel(propertyMeta));
			field.setEnabled( EditMode.READ_WRITE.equals(propertyMeta.getEditMode()) );
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
		public ButtonToMoreDetails(String id, final PropertyMeta propertyMeta)
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
			add(new Label("displayName", new BeanNameModel()));
		} 
		
	}
	
	final class BeanPropertiesListModel extends LoadableDetachableModel {
		
		protected Object load() {
			BeanInfo beanInfo = beanModel.getBeanInfo();
			if (beanInfo != null)
			{
				PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
				if (descriptors != null)
				{
					List all = new ArrayList();
					int len = descriptors.length;
					for (int i = 0; i < len; i++)
					{
						if (shouldAdd(descriptors[i]))
						{
							PropertyMeta meta = new PropertyMeta(beanModel, descriptors[i]);
							all.add(meta);
						}
					}
					int defaultPropertyIndex = beanInfo.getDefaultPropertyIndex();
					if (defaultPropertyIndex != -1)
					{
						//TODO 
					}
					return all;
				}
			}
			return Collections.EMPTY_LIST;
		} 
		
		/**
		 * Whether this descriptor should be added to the list.
		 * @param descriptor
		 * @return whether this descriptor should be added to the list
		 */
		private boolean shouldAdd(PropertyDescriptor descriptor)
		{
			if ("class".equals(descriptor.getName())) {
				return false;
			} 
			else if( descriptor.getReadMethod()==null && descriptor.getWriteMethod()==null ) { 
				return false;
			}
			
			return true;
		}
	}
	
	final class BeanNameModel extends LoadableDetachableModel {

		protected Object load() {
			BeanInfo beanInfo = beanModel.getBeanInfo();

			if (beanInfo != null)
			{
				BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
				String displayName;

				if (beanDescriptor != null)
				{
					displayName = beanDescriptor.getDisplayName();
				}
				else
				{
					Class clazz = beanModel.getBean().getClass();
					displayName = (clazz != null) ? clazz.getName() : null;
				}
				return displayName;
			}

			return null;
		} 
		
	}
}
