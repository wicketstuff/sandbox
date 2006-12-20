/*
 * Created on Dec 22, 2004
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson
 */
public class StringAttribute extends DefaultAttribute
{
	private int minLength = 0;
	private int maxLength = -1;
	private boolean multiLine = false;
	/** This is a regular expression pattern for validating */
	private String pattern = null;

	public StringAttribute(String name)
	{
		super(name);
	}

	public void visit(TypeVisitor typeVisitor)
	{
		typeVisitor.handleStringAttribute(this);
	}

	public int getMaxLength()
	{
		return maxLength;
	}

	public int getMinLength()
	{
		return minLength;
	}

	public boolean isMultiLine()
	{
		return multiLine;
	}

	public String getPattern()
	{
		return pattern;
	}

	public StringAttribute setMultiLine(boolean multiLine)
	{
		this.multiLine = multiLine;
		return this;
	}

	public StringAttribute setPattern(String pattern)
	{
		this.pattern = pattern;
		return this;
	}

	public StringAttribute setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}

	public StringAttribute setMinLength(int minLength)
	{
		this.minLength = minLength;
		return this;
	}

	public StringAttribute setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
		return this;
	}

	public StringAttribute setRequired(boolean required)
	{
		this.required = required;
		return this;
	}

	public StringAttribute setTitle(String title)
	{
		this.title = title;
		return this;
	}

}