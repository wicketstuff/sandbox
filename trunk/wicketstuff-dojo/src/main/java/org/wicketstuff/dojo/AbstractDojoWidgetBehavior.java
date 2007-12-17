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
package org.wicketstuff.dojo;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.skin.manager.SkinManager;
import org.wicketstuff.dojo.widgetloadingpolicy.DefaultLoadingPolicy;
import org.wicketstuff.dojo.widgetloadingpolicy.IDojoWidgetLoadingPolicy;

/**
 * A behavior to use instead of {@link AbstractRequireDojoBehavior} to load 
 * dojo widget.
 * <p>
 * This behavior will automatically add the widget id to the <code>djConfig.searchIds<code>
 * in order to be parse by dojo, It will also take care to recreate widget if it is rerender 
 * via {@link AjaxRequestTarget}
 * </p>
 * @author Vincent Demay
 */
public abstract class AbstractDojoWidgetBehavior extends AbstractRequireDojoBehavior
{
	private boolean deportWidgetInit = false;
	private IDojoWidgetLoadingPolicy loadingPolicy;
	
	public AbstractDojoWidgetBehavior()
	{
		this(null);
	}
	
	public AbstractDojoWidgetBehavior(IDojoWidgetLoadingPolicy loadingPolicy)
	{
		super();
		if (loadingPolicy == null){
			loadingPolicy = new DefaultLoadingPolicy();
		}
		this.loadingPolicy = loadingPolicy;
	}
	
	/**
	 * Check if the component is a {@link IDojoWidget}
	 * and add its dojoType
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#onBind()
	 */
	@Override
	protected void onBind()
	{
		super.onBind();
		if (!(getComponent() instanceof IDojoWidget)){
			throw new WicketRuntimeException("AbstractDojoWidgetBehavior should be added to a IDojoWidget : see " + getComponent().getClass().getCanonicalName());
		}
		String dojoType = ((IDojoWidget)getComponent()).getDojoType();
		if (dojoType == null){
			throw new WicketRuntimeException(getComponent().getClass().getCanonicalName() + " implements IDojoWidget, It should return a dojoType");
		}
		getComponent().add(new AttributeModifier(DojoIdConstants.DOJO_TYPE, true, new Model(dojoType)));
	}
	

	/**
	 * Deals with Dojo parser and Id to search
	 * @see org.wicketstuff.dojo.AbstractRequireDojoBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		//Dojo auto parsing is disactivated so we declare here each widget we need to parse with dojo
		if (!(RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)) {
			loadingPolicy.renderHead(response, getComponent());
		}
	}
	
	protected void respond(AjaxRequestTarget target) {}

	/**
	 * Add some needed tag on markup to be used by Dojo
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("widgetId", getComponent().getMarkupId());
		SkinManager.getInstance().setSkinOnComponent(getComponent(), this, tag);
	}
	
	/**
	 * @see org.wicketstuff.dojo.AbstractRequireDojoBehavior#onComponentReRendered(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget)
	{
		super.onComponentReRendered(ajaxTarget);
		loadingPolicy.onComponentReRendered(ajaxTarget);
	}

}
