/*
 * $Id$ $Revision$ $Date$
 * 
 * ================================================================================
 * Copyright (c) All rechten voorbehouden.
 */
package wicket.contrib.markup.html.form.fvalidate;

/**
 * Encapsulates an fValidate validator.
 * 
 * @author Eelco Hillenius
 */
public final class ValidationSpec
{
	/**
	 * The fValidate code for the given validator like: 'length|6' or
	 * 'number|0|1|120'. (see &lt;a
	 * href="http://www.peterbailey.net/fValidate/types"&gt;codes/
	 * types&lt;/a&gt;)
	 */
	private String code;

	/**
	 * The message to be used when this field does not validate. NOTE: it does
	 * not seem to be possible to use seperate messages for 'required' and
	 * another validation. Hence a fValidate validation can only have one
	 * message.
	 */
	private String errorMsg;

	/**
	 * Gets code.
	 * @return code
	 */
	public final String getCode()
	{
		return code;
	}

	/**
	 * Sets code.
	 * @param code code
	 */
	public final void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * Gets msg.
	 * @return msg
	 */
	public final String getErrorMsg()
	{
		return errorMsg;
	}

	/**
	 * Sets msg.
	 * @param msg msg
	 */
	public final void setErrorMsg(String msg)
	{
		this.errorMsg = msg;
	}
}
