package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.calendar.DojoWeekOrganizer;
import wicket.contrib.dojo.markup.html.calendar.model.WeekTimeRange;
import wicket.markup.html.WebPage;
import wicket.model.Model;

public class WeekOrganizerSample extends WebPage {
	
	private static WeekTimeRange times = new WeekTimeRange();
	
	public WeekOrganizerSample(PageParameters parameters){
		
		DojoWeekOrganizer organizer = new DojoWeekOrganizer(this, "organizer", new Model<WeekTimeRange>(times)); 
		
	}
}
