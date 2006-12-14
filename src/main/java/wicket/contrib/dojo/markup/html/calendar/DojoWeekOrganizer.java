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
package wicket.contrib.dojo.markup.html.calendar;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.contrib.dojo.markup.html.calendar.model.WeekTimeRange;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;
import static wicket.contrib.dojo.DojoIdConstants.*;

/**
 * <p>
 * 	A WeekCalendar allows to graphicaly select time range on a week à la GoogleCalendar
 * </p>
 * <p>
 * <b>Sample</b>
 * 	<pre>
 * public class WeekOrganizerSample extends WebPage {
 *	
 *	private static WeekTimeRange times = new WeekTimeRange();
 *	
 *	public WeekOrganizerSample(PageParameters parameters){
 *		
 *		DojoWeekOrganizer organizer = new DojoWeekOrganizer(this, "organizer", new Model&lt;WeekTimeRange>(times)); 
 *		
 *	}
 *}
 *
 *
 *  </pre>
 *  <u>Be carrefull</u> : Model associated with this widget should be {@link WeekTimeRange}
 * </p>
 * @author Vincent Demay
 *
 */
public class DojoWeekOrganizer extends WebMarkupContainer {

	/**
	 * Contructor
	 * @param parent parent where the widget will be added
	 * @param id widget id
	 * @param model model associated with the widget. Should be {@link WeekTimeRange}
	 */
	public DojoWeekOrganizer(MarkupContainer parent, String id, IModel<WeekTimeRange> model)
	{
		super(parent, id, model);
		this.add(new DojoWeekOrganizerHandler());
	}
	
	/**
	 * set the model associated with the widget. Should be {@link WeekTimeRange}
	 * @param model model associated with the widget. Should be {@link WeekTimeRange}
	 * @return component
	 */
	public Component setModel(IModel model)
	{
		if (!(model.getObject() instanceof WeekTimeRange)){
			throw new WicketRuntimeException("Model for a WeekOrganizer should be a WeekTimeRange instance");
		}
		return super.setModel(model);
	}

	/**
	 * Contructor
	 * @param parent parent where the widget will be added
	 * @param id widget id
	 */
	public DojoWeekOrganizer(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.add(new DojoWeekOrganizerHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_WEEKCALENDAR);
		tag.put("widgetId", getMarkupId());
	}

}
