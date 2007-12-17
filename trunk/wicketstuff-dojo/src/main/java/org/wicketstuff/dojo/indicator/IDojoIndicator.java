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
package org.wicketstuff.dojo.indicator;

import org.apache.wicket.ajax.IAjaxCallDecorator;

/**
 * Interface to used to create new Indicators
 * @author Vincent Demay
 *
 */
public interface IDojoIndicator {

	/**
	 * return a markup id that shown/hidden when a request is in the flight
	 * @return a markup id that shown/hidden when a request is in the flight
	 */
	public String getDojoIndicatorMarkupId();

	/**
	 * return an {@link IAjaxCallDecorator} to execute js on dojo request
	 * @return an {@link IAjaxCallDecorator} to execute js on dojo request
	 */
	public IAjaxCallDecorator getDojoCallDecorator();

}
