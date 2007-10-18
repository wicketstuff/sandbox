package org.wicketstuff.yui.helper;

/**
 * Inline Style 
 * @author josh
 *
 */
public class CSSInlineStyle extends NameValuePair<CSSInlineStyle>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * construct
	 */
	public CSSInlineStyle()
	{
		super();
	}

	@Override
	public String getPrefix()
	{
		return "";
	}

	@Override
	public String getSuffix()
	{
		return "";
	}

	@Override
	public String getValueSeparator()
	{
		return ";";
	}

	@Override
	public String getNameValueSeparator()
	{
		return ":";
	}

	/**
	 * adds the extra ";"
	 */
	@Override
	public String toString()
	{
		String ret = super.toString();
		
		if ((ret != null) && (ret.length() > 0))
		{
			ret = ret + getValueSeparator();
		}
		return ret;
	}
}
