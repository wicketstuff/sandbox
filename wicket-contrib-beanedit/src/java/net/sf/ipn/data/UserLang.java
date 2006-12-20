package net.sf.ipn.data;

public class UserLang extends net.sf.ipn.data.auto._UserLang
{

	/**
	 * Sets the default values during construction.
	 */
	public UserLang()
	{
		this.setPrimaryFlag(new Short((short)0));
	}

	/**
	 * Returns the code from the lang instance
	 * @return
	 */
	public String getCode()
	{
		if (this.getLang() == null)
			return null;
		return this.getLang().getCode();
	}

	/**
	 * Returns the description from the lang instance
	 * @return
	 */
	public String getDescription()
	{
		if (this.getLang() == null)
			return null;
		return this.getLang().getDescription();
	}

}
