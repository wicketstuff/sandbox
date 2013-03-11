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
package org.wicketstuff.calendar.markup.html.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.DateConverter;
import org.apache.wicket.util.string.Strings;
import org.joda.time.DateTime;


/**
 * Pops up a YUI calendar component so that the user can select a date. On
 * selection, the date is set in the component it is coupled to, after which the
 * popup is closed again. This behavior can only be used with components that
 * either implement {@link ITextFormatProvider} or that use
 * {@link DateConverter} configured with an instance of {@link SimpleDateFormat}
 * (like Wicket's default configuration has).
 * 
 * To use, simply add a new instance to your component, which would typically a
 * TextField, like {@link DateTextField}.
 * 
 * @author eelcohillenius
 */
public class DatePicker extends AbstractBehavior implements IHeaderContributor
{

	private static final long serialVersionUID = 1L;

	/** The target component. */
	private Component component;

	/**
	 * Construct.
	 */
	public DatePicker()
	{
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	public void bind(Component component)
	{
		checkComponentProvidesDateFormat(component);
		component.setOutputMarkupId(true);
		this.component = component;
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#onRendered(org.apache.wicket.Component)
	 */
	public void onRendered(Component component)
	{
		super.onRendered(component);
		// Append the span and img icon right after the rendering of the
		// component. Not as pretty as working with a panel etc, but works
		// for behaviors and is more efficient
		Response response = component.getResponse();
		response.write("\n<span>&nbsp;<img style=\"");
		response.write(getIconStyle());
		response.write("\" id=\"");
		response.write(getIconId());
		response.write("\" src=\"");
		CharSequence iconUrl = getIconUrl();
		//displayCalendar(document.forms[0].theDate,'yyyy/mm/dd',this)
		response.write(Strings.escapeMarkup(iconUrl != null ? iconUrl.toString() : ""));
		response.write("\" onclick=\"displayCalendar(document.getElementById('");
		response.write(component.getMarkupId());
		response.write("'),'");
		String datePattern = getDatePattern().replaceAll("mm","ii").toLowerCase();
		datePattern = datePattern.replace('s', '0'); // (mili)seconds are not supported
		response.write(datePattern);
		if (datePattern.indexOf("h") == -1)
		{
			response.write("',this)\"");
		}
		else
		{
			response.write("',this,true)\"");
		}
		response.write(" /></span>");
	}

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		// NOTE JavascriptResourceReference takes care of stripping comments
		// when in deployment (production) mode
		response.renderJavascriptReference(new JavascriptResourceReference(DatePicker.class,
				"dhtmlgoodies_calendar.js"));
		response.renderCSSReference(new CompressedResourceReference(DatePicker.class,
				"dhtmlgoodies_calendar.css"));

		// Set the icons image path 
		response.renderOnLoadJavascript("setImagePath(\"" + RequestCycle.get().urlFor(new ResourceReference(DatePicker.class, "images/")) + "\")");
		// Set the language code of the current session
		response.renderOnLoadJavascript("setLanguageCode(\"" + component.getLocale().getLanguage() + "\")");
	}

	/**
	 * Check that this behavior can get a date format out of the component it is
	 * coupled to. if you override this method to allow for other types (such as
	 * your own), you should override {@link #getDatePattern()} as well. This
	 * method should return normally if the component is accepted or throw a RTE
	 * when it is not.
	 * 
	 * @param component
	 *            the component this behavior is being coupled to
	 * @throws WicketRuntimeException
	 *             if the component is not support.
	 */
	protected void checkComponentProvidesDateFormat(Component component)
	{

		if (component instanceof ITextFormatProvider)
		{
			// were ok
			return;
		}

		IConverter converter = component.getConverter(DateTime.class);
		if (converter == null)
		{
			converter = component.getConverter(Date.class);
		}
		if (converter instanceof DateConverter)
		{

			return; // This is ok
		}
		throw new WicketRuntimeException(
				"this behavior can only be added to components that either implement "
						+ ITextFormatProvider.class.getName() + " or that use "
						+ DateConverter.class.getName() + " configured with an instance of "
						+ SimpleDateFormat.class.getName()
						+ " (like Wicket's default configuration has)");
	}

	/**
	 * Gets the date pattern to use for putting selected values in the coupled
	 * component. If you override this method to support components that would
	 * otherwise not be supported, you should override
	 * {@link #checkComponentProvidesDateFormat(Component)} and let it return
	 * normally.
	 * 
	 * @return The date pattern
	 */
	protected String getDatePattern()
	{

		if (component instanceof ITextFormatProvider)
		{
			return ((ITextFormatProvider)component).getTextFormat();
		}
		else
		{
			// cast from hell, but we checked before whether we could
			IConverter converter = component.getConverter(DateTime.class);
			if (converter == null)
			{
				converter = component.getConverter(Date.class);
			}
			return ((SimpleDateFormat)((DateConverter)converter).getDateFormat(component
					.getLocale())).toPattern();
		}
	}

	/**
	 * Gets the id of the icon that triggers the popup.
	 * 
	 * @return The id of the icon
	 */
	protected final String getIconId()
	{
		return component.getMarkupId() + "Icon";
	}

	/**
	 * Gets the style of the icon that triggers the popup.
	 * 
	 * @return The style of the icon, e.g. 'cursor: point' etc.
	 */
	protected String getIconStyle()
	{
		return "cursor: pointer; border: none;";
	}

	/**
	 * Gets the url for the popup button. Users can override to provide their
	 * own icon URL.
	 * 
	 * @return the url to use for the popup button/ icon
	 */
	protected CharSequence getIconUrl()
	{
		return RequestCycle.get().urlFor(new ResourceReference(DatePicker.class, "images/icon1.gif"));
	}
}
