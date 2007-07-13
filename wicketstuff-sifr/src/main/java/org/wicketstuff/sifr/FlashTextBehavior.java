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
package org.wicketstuff.sifr;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;

/**
 * FlashTextBehavior uses sIFR 2.0 (http://www.mikeindustries.com/sifr/) to decorate text
 * with "a method to insert rich typography into web pages without sacrificing 
 * accessibility, search engine friendliness, or markup semantics."
 * 
 * @author Janne Hietam&auml;ki
 * 
 */
public class FlashTextBehavior extends AbstractFlashTextBehavior
{

	private static final long serialVersionUID = 1L;

	// References to the fonts that can be used

	public final static ResourceReference VANDENKEERE_FONT = new ResourceReference(FlashTextBehavior.class, "vandenkeere.swf");
	public final static ResourceReference TRADEGOTHIC_FONT = new ResourceReference(FlashTextBehavior.class, "tradegothic.swf");

	public FlashTextBehavior()
	{
		this(TRADEGOTHIC_FONT);
	}

	FlashTextSettings settings;

	public FlashTextBehavior(ResourceReference font)
	{
		this(font, null);
	}

	public FlashTextBehavior(ResourceReference font, String color)
	{
		settings = new FlashTextSettings(font);
		settings.setColor(color);
	}

	public void setTransparent(boolean value)
	{
		settings.setTransparent(value);
	}

	@Override
	public void bind(Component component)
	{
		component.setOutputMarkupId(true);
	}

	@Override
	public void onRendered(Component component)
	{
		final String id = component.getMarkupId();
		replaceElement(component, "#" + id, settings);
	}
}
