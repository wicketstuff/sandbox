package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.contrib.dojo.markup.html.calendar.DojoWeekOrganizer;
import wicket.contrib.dojo.markup.html.calendar.model.WeekTimeRange;
import wicket.markup.html.WebPage;
import wicket.model.Model;

public class WeekOrganizerSample extends WebPage {
	
	private static WeekTimeRange times = new WeekTimeRange();
	
	public WeekOrganizerSample(PageParameters parameters){
		
		final DojoWeekOrganizer organizer = new DojoWeekOrganizer(this, "organizer", new Model<WeekTimeRange>(times)); 
		
		AjaxLink link = new AjaxLink(this, "link"){
			public void onClick(AjaxRequestTarget target) {
				target.addComponent(organizer);
			}
			
		};
	}
}
