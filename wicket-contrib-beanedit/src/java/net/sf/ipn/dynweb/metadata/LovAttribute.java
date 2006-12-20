/*
 * Created on Dec 23, 2004
 */
package net.sf.ipn.dynweb.metadata;

import java.util.List;

/**
 * @author Jonathan Carlson LOV = List Of Values. This represents an attribute containing
 *         one of a relatively small number of possible instances.
 */
public class LovAttribute extends DefaultAttribute
{
	private List options;

	public LovAttribute(String name)
	{
		super(name);
	}

	public LovAttribute(String name, List values)
	{
		super(name);
		setValues(values);
	}

	public void visit(TypeVisitor visitor)
	{
		visitor.handleLovAttribute(this);
	}

	public List getValues()
	{
		return options;
	}

	public LovAttribute setValues(List options)
	{
		this.options = options;
		return this;
	}

	/*
	 * ======================================================================== Setters
	 * for superclass attributes. They return this subclass
	 * ========================================================================
	 */

	public LovAttribute setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
		return this;
	}

	public LovAttribute setRequired(boolean required)
	{
		this.required = required;
		return this;
	}

	public LovAttribute setTitle(String title)
	{
		this.title = title;
		return this;
	}

}