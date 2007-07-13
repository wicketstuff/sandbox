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

/**
 * Base class for FlashTextBehavior.
 * 
 * @author Janne Hietam&auml;ki
 * 
 */
import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;

public class AbstractFlashTextBehavior extends AbstractBehavior implements IHeaderContributor
{
	private static final long serialVersionUID = 1L;

	private static final ResourceReference SIFR_JS = new JavascriptResourceReference(FlashTextBehavior.class, "sifr.js");
	private static final ResourceReference SIFR_SCREEN_CSS = new ResourceReference(FlashTextBehavior.class, "sifr-screen.css");

	private static final ResourceReference SIFR_PRINT_CSS = new ResourceReference(FlashTextBehavior.class, "sifr-print.css");

	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(SIFR_JS);
		response.renderCSSReference(SIFR_SCREEN_CSS, "screen");
		response.renderCSSReference(SIFR_PRINT_CSS, "print");
	}

	protected void replaceElement(Component component, String id, FlashTextSettings settings)
	{
		Response response = component.getResponse();

		response.write(JavascriptUtils.SCRIPT_OPEN_TAG);
		response.write("if(typeof sIFR == \"function\")");
		response.write("sIFR.replaceElement(\"" + id + "\", ");
		response.write("named({sFlashSrc: \"" + RequestCycle.get().urlFor(settings.font) + "\"");

		if (settings.color != null) {
			response.write(", sColor:\"" + settings.color + "\"");
		}

		if (settings.transparent) {
			response.write(", sWmode: \"transparent\"");
		}
		response.write("}));");
		response.write(JavascriptUtils.SCRIPT_CLOSE_TAG);

	}

	public static class FlashTextSettings implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public FlashTextSettings(ResourceReference font)
		{
			this.font = font;
		}

		ResourceReference font;
		String color;
		boolean transparent = true;

		public ResourceReference getFont()
		{
			return font;
		}

		public void setFont(ResourceReference font)
		{
			this.font = font;
		}

		public String getColor()
		{
			return color;
		}

		public void setColor(String color)
		{
			this.color = color;
		}

		public boolean isTransparent()
		{
			return transparent;
		}

		public void setTransparent(boolean transparent)
		{
			this.transparent = transparent;
		}

	}
}
