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

import java.io.Serializable;

import wicket.Component;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Fragment;
import wicket.model.ResourceModel;

/**
 * Panel for generic bean displaying/ editing.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public class BeanPanel extends AbstractBeanPanel
{
	private static final long serialVersionUID = 1L;


	protected BeanModel beanModel;

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
	public BeanPanel(String id, final BeanModel beanModel)
	{
		super(id, beanModel);
		this.beanModel = beanModel;
		setRenderBodyOnly(true);

		Fragment header = newHeader("header", beanModel);
		if (header == null)
		{
			throw new NullPointerException("header must be not null");
		}
		add( header );
		
		add( new ListView("propertiesList", beanModel.getProperties() ) {
			protected void populateItem(ListItem item) 
			{
				// the bean property meta-data
				IPropertyMeta propertyMeta = (IPropertyMeta)item.getModelObject();
				// lookup for wicket-style properties
				ResourceModel labelModel = new ResourceModel(propertyMeta.getName(), propertyMeta.getLabel());
				item.add(new Label("displayName", labelModel));
				// get the editor web component
				Component propertyEditor = newPropertyEditor("editor", propertyMeta, beanModel);
				if (propertyEditor == null)
				{
					propertyEditor = new Label("editor", "(editor not available)");
				}
				item.add(propertyEditor);
			} } );
	}
}
