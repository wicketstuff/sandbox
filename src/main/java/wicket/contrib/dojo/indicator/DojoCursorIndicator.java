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
package wicket.contrib.dojo.indicator;

import wicket.ajax.IAjaxCallDecorator;
import wicket.contrib.dojo.indicator.behavior.DojoIndicatorBehavior;

/**
 * A Dojo Request indicator showing an Image on the cursor when a request is in the flight
 * This Component has to be used in a {@link DojoIndicatorBehavior}
 * @author Vincent Demay
 *
 */
public class DojoCursorIndicator implements IDojoIndicator, IAjaxCallDecorator
{

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.indicator.IDojoIndicator#getDojoCallDecorator()
	 */
	public IAjaxCallDecorator getDojoCallDecorator()
	{
		return this;
	}

	public String getDojoIndicatorMarkupId()
	{
		//no Image will be shown with this kind of indicator
		return null;
	}
	
	
	

	/* (non-Javadoc)
	 * @see wicket.ajax.IAjaxCallDecorator#decorateOnFailureScript(java.lang.CharSequence)
	 */
	public CharSequence decorateOnFailureScript(CharSequence script)
	{
		return "document.getElementsByTagName('body')[0].style.cursor = 'auto';" + script;
	}

	/* (non-Javadoc)
	 * @see wicket.ajax.IAjaxCallDecorator#decorateOnSuccessScript(java.lang.CharSequence)
	 */
	public CharSequence decorateOnSuccessScript(CharSequence script)
	{
		return "document.getElementsByTagName('body')[0].style.cursor = 'auto';" + script;
	}

	/* (non-Javadoc)
	 * @see wicket.ajax.IAjaxCallDecorator#decorateScript(java.lang.CharSequence)
	 */
	public CharSequence decorateScript(CharSequence script)
	{
		return "document.getElementsByTagName('body')[0].style.cursor = 'wait';" + script;
	}

}
