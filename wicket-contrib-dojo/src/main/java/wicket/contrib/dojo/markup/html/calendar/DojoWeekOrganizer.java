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

public class DojoWeekOrganizer extends WebMarkupContainer {

	public DojoWeekOrganizer(MarkupContainer parent, String id, IModel<WeekTimeRange> model)
	{
		super(parent, id, model);
		this.add(new DojoWeekOrganizerHandler());
	}
	

	public Component setModel(IModel model)
	{
		if (!(model.getObject() instanceof WeekTimeRange)){
			throw new WicketRuntimeException("Model for a WeekOrganizer should be a WeekTimeRange instance");
		}
		return super.setModel(model);
	}

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
