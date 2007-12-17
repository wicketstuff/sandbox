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
package org.wicketstuff.dojo.markup.html.container;

import org.wicketstuff.dojo.IDojoWidget;


/**
 * Interface defining a DojoContainer, Directly implentede by {@link AbstractDojoContainer}
 * Only this king of component can be used as child of an other IDojoContainer
 * 
 * @author Vincent Demay
 *
 */
public interface IDojoContainer extends IDojoWidget {

	/**
	 * Return the Container title
	 * @return container title
	 */
	public String getTitle();
	
	
	/**
	 * Set the container title
	 * @param title container title
	 */
	public void setTitle(String title);
	
	/**
	 * Return the markupid of this component
	 * @return
	 */
	public String getMarkupId();
}
