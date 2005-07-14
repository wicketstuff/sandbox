package net.sf.ipn.data;

import java.util.List;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.Expression;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

public class PrayerItem extends net.sf.ipn.data.auto._PrayerItem
{

	public static PrayerItem withId(Long id, DataContext dc)
	{
		PrayerItem temp = null;
		SelectQuery query = new SelectQuery(PrayerItem.class, ExpressionFactory
				.matchDbExp("ID", id));
		List results = dc.performQuery(query);
		if (results.size() > 0)
		{
			temp = (PrayerItem)results.get(0);
		}
		return temp;
	}

	public String toString()
	{
		return "PrayerItem[" + " lang: " + getLang() + " group: " + getPrayerGroup().getClass()
				+ " subject: " + getSubject() + " type: " + getType() + " status: " + getStatus()
				+ "]";
	}

	/*
	 * public String getStatusCode() { PrayerItemStatus status = getStatus(); if (status ==
	 * null) return null; return status.getCode(); } public void setStatusCode(String
	 * code) { if (code == null) throw new IllegalArgumentException("code arg cannot be
	 * null."); PrayerItemStatus status = PrayerItemStatus.withCode(code,
	 * getDataContext()); if (status == null) throw new IllegalArgumentException(
	 * "PrayerItemStatus with code '" + code + "' not found."); setStatus(status); }
	 * public String getTypeCode() { PrayerItemType temp = getType(); if (temp == null)
	 * return null; return temp.getCode(); } public void setTypeCode(String code) { if
	 * (code == null) throw new IllegalArgumentException("code arg cannot be null.");
	 * PrayerItemType temp = PrayerItemType.withCode(code, getDataContext()); if (temp ==
	 * null) throw new IllegalArgumentException( "PrayerItemStatus with code '" + code + "'
	 * not found."); setType(temp); } public String getLangCode() { Lang temp = getLang();
	 * if (temp == null) return null; return temp.getCode(); } public void
	 * setLangCode(String code) { if (code == null) throw new
	 * IllegalArgumentException("code arg cannot be null."); Lang temp =
	 * Lang.withCode(code, getDataContext()); if (temp == null) throw new
	 * IllegalArgumentException( "Lang with code '" + code + "' not found.");
	 * setLang(temp); } public void setPrayerGroupId(Long id) { PrayerGroup g =
	 * PrayerGroup.withId(id, getDataContext()); if (g == null) throw new
	 * IllegalArgumentException("prayerGroupId does not exist: " + id); setPrayerGroup(g); }
	 */

	public Long getId()
	{
		return (Long)getObjectId().getIdSnapshot().get(ID_PK_COLUMN);
	}

}
