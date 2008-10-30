/**
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

package org.wicketstuff.calendarviews;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.calendarviews.logic.DateMidnightIterator;
import org.wicketstuff.calendarviews.model.IEvent;
import org.wicketstuff.calendarviews.model.IEventProvider;
import org.wicketstuff.calendarviews.util.Comparators;

/**
 * @author Jeremy Thomerson
 */
public class LargeView extends Panel {
	private static final Logger LOGGER = LoggerFactory.getLogger(LargeView.class);
	
	private static final long serialVersionUID = 1L;

	private final Date mStartDate;
	private final Date mEndDate;
	private IEventProvider mEventProvider;
	
	public LargeView(String id, Date startDate, Date endDate, IEventProvider eventProvider) {
		super(id);
		add(HeaderContributor.forCss(new ResourceReference(LargeView.class, "calendars.css")));
		if (startDate == null || endDate == null || eventProvider == null) {
			throw new IllegalArgumentException("no null parameters are allowed in this constructor");
		}
		mStartDate = startDate;
		mEndDate = endDate;
		mEventProvider = eventProvider;

		IDataProvider<DateMidnight> dp = createDaysDataProvider();
		Collection<? extends IEvent> allEvents = mEventProvider.getObject();
		final Map<DateMidnight, List<IEvent>> mapOfEvents = convertToMap(allEvents);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Data provider: " + dp);
		}
		add(new LargeGridView("rows", dp, mapOfEvents).setColumns(getNumberOfColumns()));
	}
	
	protected ListView<IEvent> createEventListView(String id, IModel<List<IEvent>> model) {
		return new ListView<IEvent>(id, model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<IEvent> item) {
				item.add(new Label("title", new PropertyModel<String>(item.getModel(), "title")));
			}
		};
	}

	protected final Map<DateMidnight, List<IEvent>> convertToMap(Collection<? extends IEvent> allEvents) {
		// TODO: this could probably use a much more efficient algorithm
		Map<DateMidnight, List<IEvent>> map = new HashMap<DateMidnight, List<IEvent>>();
		for (IEvent event : allEvents) {
			DateMidnight start = new DateMidnight(event.getStartTime());
			DateMidnight end = start;
			if (event.getEndTime() != null && event.getEndTime().equals(event.getStartTime()) == false) {
				end = new DateMidnight(event.getEndTime());
			}
			if (end.isAfter(start)) {
				for (Iterator<DateMidnight> it = new DateMidnightIterator(start.toDateTime(), end.toDateTime()); it.hasNext(); ) {
					addEventToDate(map, it.next(), event);
				}
			} else {
				addEventToDate(map, start, event);
			}
		}
		// now sort
		for (List<IEvent> list : map.values()) {
			Collections.sort(list, Comparators.EVENT_START_DATE_ASC_COMPARATOR);
		}
		return map;
	}

	protected final void addEventToDate(Map<DateMidnight, List<IEvent>> map, DateMidnight date, IEvent event) {
		List<IEvent> events = map.get(date);
		if (events == null) {
			events = new ArrayList<IEvent>();
			map.put(date, events);
		}
		events.add(event);
	}

	protected final IDataProvider<DateMidnight> createDaysDataProvider() {
		int firstDOW = getFirstDayOfWeek();
		int lastDOW = getLastDayOfWeek();
		// TODO: is this logic right?  doing this since JODA has Sunday as day 7
		int add = firstDOW > lastDOW ? -7 : 0;
		final DateTime start = new DateTime(mStartDate).withDayOfWeek(firstDOW).plusDays(add);
		final DateTime end = new DateTime(mEndDate).withDayOfWeek(lastDOW);
		final Period period = new Period(start, end, PeriodType.days());

		mEventProvider.initializeWithDateRange(start.toDate(), end.toDate());
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("start: " + start + "; end: " + end + "; days: " + period.getDays());
		}
		
		return new IDataProvider<DateMidnight>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends DateMidnight> iterator(final int first, int count) {
				return createDateMidnightIterator(start, end, first, count);
			}

			@Override
			public IModel<DateMidnight> model(DateMidnight object) {
				return new Model<DateMidnight>(object);
			}

			@Override
			public int size() {
				return period.getDays() + 1;
			}

			@Override
			public void detach() {
				// no-op
			}
			
			@Override
			public String toString() {
				return "LargeView#DaysDataProvider [size: " + size() + "]";
			}
		};
	}

	protected final int getNumberOfColumns() {
		return 7;
	}

	protected final int getLastDayOfWeek() {
		return 6;
	}

	protected final int getFirstDayOfWeek() {
		return 7;
	}
	
	protected Iterator<? extends DateMidnight> createDateMidnightIterator(DateTime start, DateTime end, int first, int count) {
		return new DateMidnightIterator(start, end, first, count);
	}

	@Override
	protected void onDetach() {
		super.onDetach();
	}

	public static LargeView createWeeksView(String id, IEventProvider eventProvider, int weeks) {
		// TODO add a similar method that allows an offset of weeks (i.e. 3 weeks, starting two weeks past today)
		Date start = new Date();
		Date end = new DateTime(start).plusWeeks(weeks - 1).toDate();
		System.out.println("Weeks view from: " + start + " to: " + end);
		return new LargeView(id, start, end, eventProvider);
	}

	public static LargeView createMonthView(String id, IEventProvider eventProvider) {
		Date start = new DateTime().dayOfMonth().setCopy(1).toDate();
		Date end = new DateTime(start).plusMonths(1).minusDays(1).toDate();
		System.out.println("Month view from: " + start + " to: " + end);
		return new LargeView(id, start, end, eventProvider);
	}
	
	private class LargeGridView extends GridView<DateMidnight> {
		private static final long serialVersionUID = 1L;
		private transient Map<DateMidnight, List<IEvent>> mMapOfEvents;

		public LargeGridView(String id, IDataProvider<DateMidnight> dp, Map<DateMidnight, List<IEvent>> mapOfEvents) {
			super(id, dp);
			mMapOfEvents = mapOfEvents;
		}

		@Override
		protected void populateEmptyItem(Item<DateMidnight> item) {
			throw new UnsupportedOperationException("LargeView should not have any empty items");
		}

		@Override
		protected void populateItem(final Item<DateMidnight> item) {
			item.add(new Label("date", new PropertyModel<Integer>(item.getModel(), "dayOfMonth")));
			item.add(createEventListView("events", new AbstractReadOnlyModel<List<IEvent>>() {
				private static final long serialVersionUID = 1L;

				@Override
				public List<IEvent> getObject() {
					return mMapOfEvents.get(item.getModelObject());
				}
				
			}));
		}
		
		@Override
		protected void onDetach() {
			super.onDetach();
			mMapOfEvents = null;
		}

	}
}
