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
package wicketstuff.crud;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * Represents a {@link Component} used as property editor. Editors are used in
 * the edit screen as well as in the filter toolbar
 * 
 * @author igor.vaynberg
 * 
 */
public interface Editor
{
	/**
	 * Sets the required flag
	 * 
	 * @param required
	 */
	void setRequired(boolean required);

	/**
	 * 
	 * @return true if value is required, false otherwise
	 */
	boolean isRequired();


	/**
	 * Adds a validator to the editor
	 * 
	 * @param validator
	 */
	void add(IValidator validator);

	/**
	 * Sets editor's label
	 * 
	 * @param label
	 */
	void setLabel(IModel label);

	/**
	 * 
	 * @return editor's label
	 */
	IModel getLabel();
}
