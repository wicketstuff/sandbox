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
package wicketstuff.crud.filter;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;

/**
 * Mixin interface for columns that can be filtered
 * 
 * @author igor.vaynberg
 * 
 */
public interface IFilterableColumn extends IColumn
{
	/**
	 * Gets component that will be used to collect filter information
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model where component should store its value
	 * @return
	 */
	Component getFilter(String id, IModel model);
}
