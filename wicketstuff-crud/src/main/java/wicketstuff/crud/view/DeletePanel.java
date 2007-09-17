/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;

/**
 * Confirm delete screen
 * 
 * @author igor.vaynberg
 * 
 */
public class DeletePanel extends Panel
{

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param properties
	 * @param crudListener
	 */
	public DeletePanel(String id, IModel model, List<Property> properties,
			final ICrudListener crudListener)
	{
		super(id, model);

		add(new PropertiesView("props", model, properties));

		add(new Link("confirm", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onDeleteConfirmed(getModel());
			}

		});


		add(new Link("cancel", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onCancel();
			}

		});

	}


}
