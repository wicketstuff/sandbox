/*
 * Created on Dec 22, 2004
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson
 */
public class NumericAttribute extends DefaultAttribute
{

	/** Defaults to positive numbers only */
	private Number minValue = new Integer(0);
	/** -1 means no maximum value */
	private Number maxValue = new Integer(-1);

	public NumericAttribute(String name)
	{
		super(name);
	}

	public Number getMaxValue()
	{
		return maxValue;
	}

	public void setMaxValue(Number maxValue)
	{
		this.maxValue = maxValue;
	}

	public Number getMinValue()
	{
		return minValue;
	}

	public void setMinValue(Number minValue)
	{
		this.minValue = minValue;
	}

	public NumericAttribute setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
		return this;
	}

	public NumericAttribute setRequired(boolean required)
	{
		this.required = required;
		return this;
	}

	public NumericAttribute setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public void visit(TypeVisitor typeVisitor)
	{
		typeVisitor.handleNumericAttribute(this);
	}

}