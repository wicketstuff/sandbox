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

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.TextEditor;

/**
 * Numeric property
 * 
 * @author igor.vaynberg
 * 
 */
public class NumericProperty extends Property
{
	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public NumericProperty(String path, IModel label)
	{
		super(path, label);
	}

	/** {@inheritDoc} */
	@Override
	public Component getEditor(String id, IModel object)
	{
		TextEditor editor = new TextEditor(id, new PropertyModel(object, getPath()));
		configure(editor);
		return editor;
	}

}