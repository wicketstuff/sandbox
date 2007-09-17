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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.TextEditor;


/**
 * String property
 * 
 * @author igor.vaynberg
 * 
 */
public class StringProperty extends Property
{
	private int maxLength = 0;

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public StringProperty(String path, IModel label)
	{
		super(path, label);
	}

	/** {@inheritDoc} */
	@Override
	public Component getEditor(String id, IModel object)
	{
		TextEditor editor = new TextEditor(id, new PropertyModel(object, getPath()));
		if (maxLength > 0)
		{
			editor.setMaxLength(maxLength);
			editor.add(StringValidator.maximumLength(maxLength));
		}
		configure(editor);
		return editor;
	}


	/**
	 * Sets max length of string
	 * 
	 * @param maxLength
	 * @return
	 */
	public StringProperty setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}


}
