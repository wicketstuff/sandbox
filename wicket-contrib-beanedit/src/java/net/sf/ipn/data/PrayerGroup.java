package net.sf.ipn.data;

import java.util.List;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

public class PrayerGroup extends net.sf.ipn.data.auto._PrayerGroup
{

	public static PrayerGroup withId(Long id, DataContext dc)
	{
		PrayerGroup temp = null;
		SelectQuery query = new SelectQuery(PrayerGroup.class, ExpressionFactory.matchDbExp("ID",
				id));
		List results = dc.performQuery(query);
		if (results.size() > 0)
		{
			temp = (PrayerGroup)results.get(0);
		}
		return temp;
	}

	public Long getId()
	{
		return (Long)this.getObjectId().getValueForAttribute("ID");
	}

	public String toString()
	{
		return getName();
	}

}