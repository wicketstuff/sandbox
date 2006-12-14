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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An Helper for {@link WeekTimeRange}.
 * interpret and generate Json
 * @author Vincent Demay
 *
 */
public class TimeRangeModelHelper
{
	/**
	 * Create Json for one interval. Json will be sent client side 
	 * too upgrade the widget
	 * @param range Time range
	 * @return String representing Json
	 */
	public static String createJsonInterval(TimeRange range){
		String toReturn ="{\"start\":\"";
		toReturn += normelizeHour(range.getStartTimeHour()) + ":" + normelizeHour(range.getStartTimeMin());
		toReturn += "\",\"end\":\"";
		toReturn += normelizeHour(range.getEndTimeHour()) + ":" + normelizeHour(range.getEndTimeMin());
		toReturn += "\"}";
		return toReturn;
	}
	
	private static String normelizeHour(int val){
		String value = val + "";
		if (value.length() == 1){
			value = "0" + value;
		}
		return value;
	}
	
	/**
	 * Create Json for a {@link TimeRange} list
	 * @param timeRanges
	 * @return json
	 */
	public static String createJsonIntervals(ArrayList<TimeRange> timeRanges){
		Iterator<TimeRange> it = timeRanges.iterator();
		String toReturn = "[";
		while (it.hasNext()){
			toReturn += createJsonInterval(it.next()) + ",";
		}
		if(timeRanges.size() != 0){
			toReturn = toReturn.substring(0, toReturn.length()-1) + "]";
		}else{
			toReturn += "]";
		}
		return toReturn;
	}
	
	/**
	 * Create json for a {@link WeekTimeRange} 
	 * @param ranges
	 * @return json
	 */
	public static String createJson(WeekTimeRange ranges){
		String toReturn = "";
		toReturn += "{\"sunday\":" + createJsonIntervals(ranges.get(WeekTimeRange.SUNDAY));
		toReturn += ",\"monday\":" + createJsonIntervals(ranges.get(WeekTimeRange.MONDAY));
		toReturn += ",\"tuesday\":" + createJsonIntervals(ranges.get(WeekTimeRange.TUESDAY));
		toReturn += ",\"wednesday\":" + createJsonIntervals(ranges.get(WeekTimeRange.WEDNESDAY));
		toReturn += ",\"thursday\":" + createJsonIntervals(ranges.get(WeekTimeRange.THURSDAY));
		toReturn += ",\"friday\":" + createJsonIntervals(ranges.get(WeekTimeRange.FRIDAY));
		toReturn += ",\"saturday\":" + createJsonIntervals(ranges.get(WeekTimeRange.SATURDAY));
		toReturn += "}";
		return toReturn;
	}
	
	/**
	 * Return int associated with a day
	 * @param day day
	 * @return int representing the day
	 */
	public static int getDay(String day){
		if (day.equals("sunday")){
			return WeekTimeRange.SUNDAY;
		}else if (day.equals("monday")){
			return WeekTimeRange.MONDAY;
		}else if (day.equals("tuesday")){
			return WeekTimeRange.TUESDAY;
		}else if (day.equals("wednesday")){
			return WeekTimeRange.WEDNESDAY;
		}else if (day.equals("thursday")){
			return WeekTimeRange.THURSDAY;
		}else if (day.equals("friday")){
			return WeekTimeRange.FRIDAY;
		}else if (day.equals("saturday")){
			return WeekTimeRange.SATURDAY;
		}
		return -1;
	}
	
	/**
	 * Create a Time range knowing start and end
	 * @param start
	 * @param end
	 * @return {@link TimeRange}
	 */
	public static TimeRange createRange(String start, String end){
		TimeRange range = new TimeRange();
		range.setStartTimeHour(Integer.parseInt(start.substring(0,2)));
		range.setStartTimeMin (Integer.parseInt(start.substring(3,5)));
		
		range.setEndTimeHour(Integer.parseInt(end.substring(0,2)));
		range.setEndTimeMin (Integer.parseInt(end.substring(3,5)));
		return range;
	}
}
