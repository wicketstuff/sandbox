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

import org.apache.wicket.Component;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.DateEditor;


/**
 * Represents a property of date type
 * 
 * @author igor.vaynberg
 * 
 */
public class DateProperty extends Property
{
	private String pattern = "MM/dd/yyyy";

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public DateProperty(String path, IModel label)
	{
		super(path, label);
	}

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 * @param pattern
	 *            pattern used to format date value
	 */
	public DateProperty(String path, IModel label, String pattern)
	{
		this(path, label);
		this.pattern = pattern;
	}

	/** {@inheritDoc} */
	@Override
	public Component getEditor(String id, IModel object)
	{
		DateEditor editor = new DateEditor(id, new PropertyModel(object, getPath()), pattern);
		configure(editor);
		return editor;
	}


	/** {@inheritDoc} */
	@Override
	public Component getViewer(String id, IModel object)
	{
		return new DateLabel(id, new PropertyModel(object, getPath()), new PatternDateConverter(
				pattern, false));
	}

}
