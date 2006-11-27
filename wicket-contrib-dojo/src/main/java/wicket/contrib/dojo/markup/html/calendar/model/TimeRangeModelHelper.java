package wicket.contrib.dojo.markup.html.calendar.model;

import java.util.ArrayList;
import java.util.Iterator;

public class TimeRangeModelHelper
{
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
	
	public static TimeRange createRange(String start, String end){
		TimeRange range = new TimeRange();
		range.setStartTimeHour(Integer.parseInt(start.substring(0,2)));
		range.setStartTimeMin (Integer.parseInt(start.substring(3,5)));
		
		range.setEndTimeHour(Integer.parseInt(end.substring(0,2)));
		range.setEndTimeMin (Integer.parseInt(end.substring(3,5)));
		return range;
	}
}
