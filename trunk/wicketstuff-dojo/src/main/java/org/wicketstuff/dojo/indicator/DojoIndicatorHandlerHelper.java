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

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.wicketstuff.dojo.AbstractDefaultDojoBehavior;
import org.wicketstuff.dojo.indicator.behavior.DojoIndicatorBehavior;


/**
 * This class is used by beahavior which need to manage {@link IDojoIndicator}.<br/>
 * see {@link AbstractDefaultDojoBehavior} to get an exemple of the use
 * 
 * @author Vincent Demay
 *
 */
public class DojoIndicatorHandlerHelper {

	private DojoIndicatorBehavior indicatorBehavior = null;
	
	private Component component;
	
	public DojoIndicatorHandlerHelper(Component component) {
		super();
		this.component = component;
	}

	/**
	 * If a DojoIndicator has already been found does not looking for it again
	 * @return the dojoIndicator id
	 */
	private DojoIndicatorBehavior getIndicator(){
		if (indicatorBehavior == null){
			Iterator behaviors = component.getBehaviors().iterator();
			while (behaviors.hasNext()){
				Object behavior = behaviors.next();
				if (behavior instanceof DojoIndicatorBehavior){
					indicatorBehavior = (DojoIndicatorBehavior)behavior;
				}
			}
		}
		return indicatorBehavior;
	}

	/**
	 * return the indicator Id to show it if it is in the page
	 * @return the indicator Id to show it if it is in the page
	 */
	public String getAjaxIndicatorMarkupId()
	{
		if (getIndicator() != null){
			return indicatorBehavior.getDojoIndicatorMarkupId();
		}
		return null;
	}

	/**
	 * return the ajax call decorator to do more than hide or show an image
	 * @return the ajax call decorator to do more than hide or show an image
	 */
	public IAjaxCallDecorator getAjaxCallDecorator()
	{
		if (getIndicator() != null){
			return indicatorBehavior.getDojoCallDecorator();
		}
		return null;
	}
}
