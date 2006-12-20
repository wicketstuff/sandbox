package net.sf.ipn.data;

import java.util.List;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

public class PrayerItemStatus extends net.sf.ipn.data.auto._PrayerItemStatus
{

	/**
	 * Returns the PrayerItemStatus instance that has the given code.
	 * @param code
	 * @param dc - the DataContext with which to find the instance.
	 * @return an instance of PrayerItemStatus or null if none found.
	 */
	public static PrayerItemStatus withCode(String code, DataContext dc)
	{
		SelectQuery query = new SelectQuery(PrayerItemStatus.class, ExpressionFactory.matchExp(
				"code", code));
		List results = dc.performQuery(query);
		if (results.size() < 1)
			return null;
		return (PrayerItemStatus)results.get(0);
	}

	public String toString()
	{
		return getDescription();
	}
}
