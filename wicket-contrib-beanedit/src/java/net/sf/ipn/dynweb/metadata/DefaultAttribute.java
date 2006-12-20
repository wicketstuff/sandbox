/*
 * Created on Dec 28, 2004
 */
package net.sf.ipn.dynweb.metadata;

import java.io.Serializable;


/**
 * @author Jonathan Carlson TODO Provides...
 */
public abstract class DefaultAttribute implements Attribute, Serializable
{
	protected String name = null;
	protected String title = null;
	protected boolean editable = true;
	protected boolean displayable = true;
	protected boolean primaryKey = false;
	protected boolean required = false;

	public DefaultAttribute(String name)
	{
		this.name = name;
	}

	public void setDisplayable(boolean displayable)
	{
		this.displayable = displayable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public boolean isEditable()
	{
		return this.editable;
	}

	public boolean isDisplayable()
	{
		return this.displayable;
	}

	public boolean isPrimaryKey()
	{
		return primaryKey;
	}

	public boolean isRequired()
	{
		return required;
	}

	public abstract void visit(TypeVisitor visitor);

	public String getTitle()
	{
		if (this.title == null)
			return this.name;
		return title;
	}
}