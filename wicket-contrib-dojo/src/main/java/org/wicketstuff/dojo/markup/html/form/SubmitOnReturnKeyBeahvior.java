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
package org.wicketstuff.dojo.markup.html.form;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Behavior to add on {@link FormComponent} to select which submit need to be done
 * when user press return key.<br/>
 * This behavior can be added on several form component : same behavior for all component<br/>
 * 
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class SubmitOnReturnKeyBeahvior  extends AbstractBehavior implements IHeaderContributor {

	/** Component used on return key pressed to send the form */
	private Component submitComponent;
	
	/** the component that this handler is bound to. */
	private ArrayList components;
	
	/**
	 * Create the {@link SubmitOnReturnKeyBeahvior}
	 * @param submitComponent component which will be used to submit the form when user press
	 * return key
	 */
	public SubmitOnReturnKeyBeahvior(Component submitComponent) {
		super();
		if (submitComponent == null){
			throw new IllegalArgumentException("Argument submitComponent must be not null");
		}
		if (!(submitComponent instanceof FormComponent)){
			throw new IllegalArgumentException("Argument submitComponent must be instanceof FormComponent");
		}
		this.submitComponent  = submitComponent;
		this.components = new ArrayList();
	}
	
	/**
	 * Bind this handler to the given component.
	 * 
	 * @param hostComponent
	 *            the component to bind to
	 */
	public final void bind(final Component hostComponent)
	{
		if (hostComponent == null)
		{
			throw new IllegalArgumentException("Argument hostComponent must be not null");
		}
		if (!hostComponent.getOutputMarkupId())
		{
			throw new IllegalArgumentException("Argument hostComponent must have a markupId");
		}
		this.components.add(hostComponent);
	}

	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(new ResourceReference(SubmitOnReturnKeyBeahvior.class, "LockReturnEvent.js"));
		//connect enterKey on component
		String script = "";
		Iterator it = components.iterator();
		while(it.hasNext()){
			FormComponent c = (FormComponent) it.next();
			script += "dojo.addOnLoad(function() {\n" +
					"	dojo.event.connect(dojo.byId('" + c.getMarkupId() + "'), 'onKey', function(event){\n" +
					"		if (event.keyCode == 13) {\n" +
					//FIXME : does not work for a tags!!!
					"			dojo.byId('" + submitComponent.getMarkupId() + "').click();" +
					"		}\n" +
					"	});\n" +
					"});\n";
		}
		response.renderJavascript(script, toString());
	}

	
}
