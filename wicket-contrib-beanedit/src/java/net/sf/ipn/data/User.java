package net.sf.ipn.data;

import java.util.Date;
import java.util.Iterator;

import org.objectstyle.cayenne.validation.ValidationResult;

public class User extends net.sf.ipn.data.auto._User
{

	/**
	 * Do initializing things that cannot be done in the constructor. (That is, things
	 * that require a DataContext)
	 */
	public void setDefaults()
	{
		setStatusCode("enabled");
		if (getGroupList().isEmpty())
		{
			addGroup("Personal", "private");
		}
	}

	/**
	 * Add a UserEmail instance for the given email address. If it already exists for the
	 * user then quietly return. If this is the first one added, then assume it is
	 * primary.
	 */
	public void addEmailAddress(String emailAddress)
	{
		Iterator it = getUserEmailList().iterator();
		while (it.hasNext())
		{
			UserEmail temp = (UserEmail)it.next();
			if (temp.getEmailAddress().equalsIgnoreCase(emailAddress.trim()))
				return; // No need to continue on. We already have it.
		}
		UserEmail userEmail = new UserEmail();
		getDataContext().registerNewObject(userEmail);
		userEmail.setEmailAddress(emailAddress.trim());
		if (getUserEmailList().isEmpty())
			userEmail.setPrimaryFlag(new Short((short)1));
		addToUserEmailList(userEmail);
	}

	public void setPrimaryEmailAddress(String emailAddress)
	{
		Iterator it = getUserEmailList().iterator();
		boolean wasSet = false;
		while (it.hasNext())
		{
			UserEmail temp = (UserEmail)it.next();
			if (temp.getEmailAddress().equalsIgnoreCase(emailAddress))
			{
				temp.setPrimaryFlag(new Short((short)1));
				wasSet = true;
			}
			else
				temp.setPrimaryFlag(new Short((short)0));
		}
		if (!wasSet)
			throw new IllegalArgumentException("Email address not in list: '" + emailAddress + "'");
	}

	/**
	 * Add a UserEmail instance for the given email address. If it already exists for the
	 * user then quietly return. If this is the first one added, then assume it is
	 * primary.
	 */
	public void removeEmailAddress(String emailAddress)
	{
		Iterator it = getUserEmailList().iterator();
		while (it.hasNext())
		{
			UserEmail temp = (UserEmail)it.next();
			if (temp.getEmailAddress().equalsIgnoreCase(emailAddress.trim()))
			{
				removeFromUserEmailList(temp);
				getDataContext().deleteObject(temp);
			}
		}
		return;
	}

	/**
	 * Add a UserLang instance for the given language code. If it already exists for the
	 * user then quietly return. If this is the first one added, then assume it is
	 * primary.
	 */
	public void addLangCode(String code)
	{
		Lang lang = Lang.withCode(code, getDataContext());
		if (lang == null)
			throw new IllegalArgumentException("Invalid Lang code: '" + code + "'");
		Iterator it = getUserLangList().iterator();
		while (it.hasNext())
		{
			UserLang temp = (UserLang)it.next();
			if (temp.getLang().getCode().equals(code))
				return; // No need to continue on. We already have it.
		}
		UserLang userLang = new UserLang();
		getDataContext().registerNewObject(userLang);
		userLang.setLang(lang);
		if (getUserLangList().isEmpty())
			userLang.setPrimaryFlag(new Short((short)1));
		addToUserLangList(userLang);
	}

	public void setPrimaryLangCode(String code)
	{
		Lang lang = Lang.withCode(code, getDataContext());
		if (lang == null)
			throw new IllegalArgumentException("Invalid Lang code: '" + code + "'");
		Iterator it = getUserLangList().iterator();
		boolean wasSet = false;
		while (it.hasNext())
		{
			UserLang temp = (UserLang)it.next();
			if (temp.getLang().getCode().equals(code))
			{
				temp.setPrimaryFlag(new Short((short)1));
				wasSet = true;
			}
			else
				temp.setPrimaryFlag(new Short((short)0));
		}
		if (!wasSet)
			throw new IllegalArgumentException("Lang not in list: '" + code + "'");
	}

	/**
	 * Returns the language code that is primary for this user
	 * @return the language code that is primary for this user
	 */
	public String getPrimaryLangCode()
	{
		String primary = null;
		Iterator it = getUserLangList().iterator();
		while (it.hasNext())
		{
			UserLang temp = (UserLang)it.next();
			if (temp.getPrimaryFlag().shortValue() > 0)
				primary = temp.getLang().getCode();
		}
		return primary;
	}

	/**
	 * Returns the Lang instance that is primary for this user
	 * @return the Lang instance that is primary for this user
	 */
	public String getPrimaryEmailAddress()
	{
		String primary = null;
		Iterator it = getUserEmailList().iterator();
		while (it.hasNext())
		{
			UserEmail temp = (UserEmail)it.next();
			if (temp.getPrimaryFlag().shortValue() > 0)
				primary = temp.getEmailAddress();
		}
		return primary;
	}

	/**
	 * Convenience method to allow dealing with Strings
	 * @param code
	 */
	public String getStatusCode()
	{
		return getStatus().getCode();
	}

	/**
	 * Convenience method to allow dealing with Strings
	 * @param code
	 */
	public void setStatusCode(String code)
	{
		setStatus(UserStatus.withCode(code, getDataContext()));
	}

	/**
	 * Provides a convenience method for adding a group without messing with too many
	 * details.
	 */
	public void addGroup(String name, String typeCode)
	{
		PrayerGroupType type = PrayerGroupType.withCode(typeCode, getDataContext());
		if (type == null)
			throw new IllegalArgumentException("No PrayerGroupType found for code " + typeCode);

		// Create the new group and add it
		PrayerGroup pg = new PrayerGroup();
		getDataContext().registerNewObject(pg);
		pg.setName(name);
		if (getPrimaryLangCode() != null)
		{
			Lang lang = Lang.withCode(getPrimaryLangCode(), getDataContext());
			pg.setLang(lang);
		}
		pg.setOwningUser(this);
		pg.setType(type);
	}

	public PrayerGroup getGroupWithName(String name)
	{
		PrayerGroup result = null;
		Iterator it = getGroupList().iterator();
		while (it.hasNext())
		{
			PrayerGroup group = (PrayerGroup)it.next();
			if (group.getName().equals(name))
			{
				result = group;
			}
		}
		return result;
	}

	protected void validateForSave(ValidationResult vr)
	{
		if (getStatus() == null)
		{
			setStatus(UserStatus.withCode("enabled", getDataContext()));
		}
		if (getRegistrationDate() == null)
		{
			setRegistrationDate(new Date());
		}
		// TODO: Make sure one and only one primary UserLang

		// TODO: Make sure one and only one primary UserEmail

		// TODO: Make sure there is one private group called Personal

		// TODO: Make sure none of the group names match for this user
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof User))
			return false;
		User u = (User)o;
		return u.getObjectId().getValueForAttribute("ID").equals(
				getObjectId().getValueForAttribute("ID"));
	}

	public int hashcode()
	{
		return ((Long)getObjectId().getValueForAttribute("ID")).intValue();
	}

}