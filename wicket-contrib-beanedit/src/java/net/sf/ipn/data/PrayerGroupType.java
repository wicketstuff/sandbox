package net.sf.ipn.data;

import java.util.List;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

public class PrayerGroupType extends net.sf.ipn.data.auto._PrayerGroupType
{

	/**
	 * Returns the PrayerGroupType instance that has the given code.
	 * @param code
	 * @param dc - the DataContext with which to find the instance.
	 * @return an instance of PrayerGroupType or null if none found.
	 */
	public static PrayerGroupType withCode(String code, DataContext dc)
	{
		SelectQuery query = new SelectQuery(PrayerGroupType.class, ExpressionFactory.matchExp(
				"code", code));
		List results = dc.performQuery(query);
		if (results.size() < 1)
			return null;
		return (PrayerGroupType)results.get(0);
	}

}
