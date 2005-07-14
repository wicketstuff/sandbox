package net.sf.ipn.data.auto;

/**
 * Class _UserLang was generated by Cayenne. It is probably a good idea to avoid changing
 * this class manually, since it may be overwritten next time code is regenerated. If you
 * need to make any customizations, please use subclass.
 */
public class _UserLang extends org.objectstyle.cayenne.CayenneDataObject
{

	public static final String PRIMARY_FLAG_PROPERTY = "primaryFlag";
	public static final String LANG_PROPERTY = "lang";
	public static final String USER_PROPERTY = "user";

	public static final String ID_PK_COLUMN = "ID";

	public void setPrimaryFlag(Short primaryFlag)
	{
		writeProperty("primaryFlag", primaryFlag);
	}

	public Short getPrimaryFlag()
	{
		return (Short)readProperty("primaryFlag");
	}


	public void setLang(net.sf.ipn.data.Lang lang)
	{
		setToOneTarget("lang", lang, true);
	}

	public net.sf.ipn.data.Lang getLang()
	{
		return (net.sf.ipn.data.Lang)readProperty("lang");
	}


	public void setUser(net.sf.ipn.data.User user)
	{
		setToOneTarget("user", user, true);
	}

	public net.sf.ipn.data.User getUser()
	{
		return (net.sf.ipn.data.User)readProperty("user");
	}


}
