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
package wicketstuff.crud.property;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Enum property
 * 
 * @author igor.vaynberg
 * 
 * @param <T>
 */
public class EnumChoiceProperty<T extends Enum> extends ChoiceProperty
{

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 * @param type
	 *            enum type
	 */
	public EnumChoiceProperty(String path, IModel label, final Class<T> type)
	{
		super(path, label);
		setChoices(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return Arrays.asList(type.getEnumConstants());
			}

		});

		setRenderer(new EnumRenderer());

	}

	/**
	 * Renderer used to render enum
	 * 
	 * @author igor.vaynberg
	 * 
	 * @param <T>
	 */
	private static class EnumRenderer<T extends Enum> implements IChoiceRenderer
	{

		private static final long serialVersionUID = 1L;

		/** {@inheritDoc} */
		public Object getDisplayValue(Object object)
		{
			return (object == null) ? "" : object.toString();
		}

		/** {@inheritDoc} */
		public String getIdValue(Object object, int index)
		{
			return (object == null) ? "" : ((Enum)object).name();
		}

	}

}
