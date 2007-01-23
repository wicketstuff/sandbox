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
package wicket.contrib.beanpanels;


import wicket.Component;
import wicket.contrib.beanpanels.editor.PropertyEditorFactory;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.basic.Label;
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
	/**
	 * Construct.
	 * @param id component id
	 * @param beanModel model with the JavaBean to be edited or displayed
	 * @deprecated
	 */
	public AbstractBeanPanel(String id, IModel beanModel)
	{
		super(id, beanModel);
		if (beanModel == null)
		{
			throw new IllegalArgumentException("Argument BeanModel must not be null");
		}
	}

	/**
	 * Gets the header panel of this editor.
	 * @param panelId id of panel; must be used for constructing any panel
	 * @param beanModel model with the JavaBean to be edited or displayed
	 * @return the header panel
	 * @deprecated
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
	 * @deprecated
	 */
	protected Component newPropertyEditor(String panelId, IPropertyMeta propertyMeta, BeanModel beanModel)
	{
		return PropertyEditorFactory.get().create(panelId,propertyMeta,beanModel);
	}

	
	
	/**
	 * Panel for view field
	 * @deprecated
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


	/**
	 * @deprecated
	 */
	final class DefaultHeaderFragment extends Fragment {

		public DefaultHeaderFragment(String id, final BeanModel beanModel) {
			super(id, "defaultHeader");
			add(new Label("displayName", new ResourceModel(beanModel.getBean().getClass().getName()+".header", "")));
		} 
	}
	

	
}
