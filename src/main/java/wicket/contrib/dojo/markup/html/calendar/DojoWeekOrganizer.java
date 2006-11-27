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
	}

}
