package wicket.contrib.dojo.markup.html.calendar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	public void add(int day, TimeRange range){
		week.get(day).add(range);
	}
	
	public void remove(int day, TimeRange range){
		week.get(day).remove(range);
	}
	
	public ArrayList<TimeRange> get(int day){
		return week.get(day);
	}
}
