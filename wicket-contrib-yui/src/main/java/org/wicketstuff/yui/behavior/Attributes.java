package org.wicketstuff.yui.behavior;

import org.wicketstuff.yui.helper.JSObject;

public class Attributes extends JSObject<Attributes>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public Attributes()
	{
		super();
	}

	/**
	 * constructor. Adds a single value
	 * @param element
	 * @param value
	 */
	public Attributes(String element ,String value)
	{
		this.add(element, value);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		return "{}".equals(toString());
	}
}
