package net.sf.ipn.data;

import java.util.List;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

public class Role extends net.sf.ipn.data.auto._Role
{

	/**
	 * Returns the Role instance that has the given code.
	 * @param code
	 * @param dc - the DataContext with which to find the instance.
	 * @return an instance of Role or null if none found.
	 */
	public static Role withCode(String code, DataContext dc)
	{
		SelectQuery query = new SelectQuery(Role.class, ExpressionFactory.matchExp("code", code));
		List results = dc.performQuery(query);
		if (results.size() < 1)
			return null;
		return (Role)results.get(0);
	}
}
