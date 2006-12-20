/*
 * Created on Feb 1, 2005
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson
 */
public class DateAttribute extends DefaultAttribute
{

	public DateAttribute(String name)
	{
		super(name);
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.Type#visit(net.sf.ipn.uimeta.TypeVisitor)
	 */
	public void visit(TypeVisitor visitor)
	{
		visitor.handleDateAttribute(this);
	}

	/*
	 * ======================================================================== Setters
	 * for superclass attributes. They return this subclass
	 * ========================================================================
	 */

	public DateAttribute setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
		return this;
	}

	public DateAttribute setRequired(boolean required)
	{
		this.required = required;
		return this;
	}

	public DateAttribute setTitle(String title)
	{
		this.title = title;
		return this;
	}

}
