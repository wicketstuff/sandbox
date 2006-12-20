package net.sf.ipn.data;

import java.util.List;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

public class UserStatus extends net.sf.ipn.data.auto._UserStatus
{

	/**
	 * Returns the UserStatus instance that has the given code.
	 * @param code
	 * @param dc - the DataContext with which to find the instance.
	 * @return an instance of UserStatus or null if none found.
	 */
	public static UserStatus withCode(String code, DataContext dc)
	{
		SelectQuery query = new SelectQuery(UserStatus.class, ExpressionFactory.matchExp("code",
				code));
		List results = dc.performQuery(query);
		if (results.size() < 1)
			return null;
		return (UserStatus)results.get(0);
	}
}
