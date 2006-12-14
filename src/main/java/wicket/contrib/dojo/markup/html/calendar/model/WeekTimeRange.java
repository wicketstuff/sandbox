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
package wicket.contrib.dojo.markup.html.calendar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import wicket.contrib.dojo.markup.html.calendar.DojoWeekOrganizer;

/**
 * Model used by {@link DojoWeekOrganizer}
 * @author Vincent Demay
 *
 */
public class WeekTimeRange implements Serializable
{
	private HashMap<Integer, ArrayList<TimeRange>> week;

	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;

	/**
	 * Constructor
	 *
	 */
	public WeekTimeRange()
	{
		super();
		week = new HashMap<Integer, ArrayList<TimeRange>>();
		week.put(SUNDAY, new ArrayList<TimeRange>());
		week.put(MONDAY, new ArrayList<TimeRange>());
		week.put(TUESDAY, new ArrayList<TimeRange>());
		week.put(WEDNESDAY, new ArrayList<TimeRange>());
		week.put(THURSDAY, new ArrayList<TimeRange>());
		week.put(FRIDAY, new ArrayList<TimeRange>());
		week.put(SATURDAY, new ArrayList<TimeRange>());
	}

	/**
	 * Add a new Time range for a given day and a give time Range
	 * @param day day
	 * @param range Time range
	 */
	public void add(int day, TimeRange range){
		week.get(day).add(range);
	}
	
	/**
	 * Suppress an existing Time range for a given day and a give time Range
	 * @param day day
	 * @param range Time range
	 */
	public void remove(int day, TimeRange range){
		week.get(day).remove(range);
	}
	
	public ArrayList<TimeRange> get(int day){
		return week.get(day);
	}
}
