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
package org.wicketstuff.push;

import org.apache.wicket.Component;

/**
 * A push service provides a simple way to install push facility
 * on a component, and then push events to the component using
 * a {@link IPushTarget}.
 * <p>
 * Implementations of {@link IPushService} must be thread safe,
 * and thus easily reusable.
 * <p>
 * Here is a common example of usage:
 * <pre>
 * final IPushTarget pushTarget = pushService.installPush(myPage);
 * myServerEventService.addListener(new MyListener() {
 * 		public void onEvent(Event ev) {
 *     		if (pushTarget.isConnected()) {
 *				pushTarget.addComponent(myComponentToUpdate);
 *				pushTarget.appendJavascript("alert('event recevied from server:"+ev+"');");
 *				pushTarget.trigger();
 * 			} else {
 *				myServerEventService.removeListener(this);
 *			}
 *		}
 * });
 * </pre>
 * 
 * This service is usually used when you already have a facility for triggering and 
 * listening server side events that you want to propagate to the clients.
 * <p>
 * For a mechanism including an event publish/subscribe mechanism, see {@link IChannelService}
 * 
 * @author Xavier Hanin
 * 
 * @see IChannelService
 * @see IPushTarget
 */
public interface IPushService {
	/**
	 * Installs a push facility on the given component.
	 * <p>
	 * The component on which the push facility is installed doesn't really matter
	 * as soon as it is visible.
	 * <p>
	 * Usually the page is used as component.
	 * 
	 * @param component the component on which the push facility must be installed
	 * @return an {@link IPushTarget} allowing to send events to the components of the same page
	 */
	IPushTarget installPush(Component component);
}
