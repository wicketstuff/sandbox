package org.wicketstuff.simile.timeline;

import java.util.ArrayList;
import java.util.List;

public class TimelineModel {
	private List<TimelineEventModel> events = new ArrayList<TimelineEventModel>();

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineModel#getEvents()
	 */
	public List<TimelineEventModel> getEvents() {
		return events;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineModel#setEvents(java.util.List)
	 */
	public void setEvents(List<TimelineEventModel> events) {
		this.events = events;
	}
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineModel#addEvent(org.wicketstuff.simile.timeline.TimelineEventModel)
	 */
	public void addEvent(TimelineEventModel event)
	{
		events.add(event);
	}
}
