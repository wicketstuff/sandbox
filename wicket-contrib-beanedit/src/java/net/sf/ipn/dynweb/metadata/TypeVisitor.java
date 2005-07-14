/*
 * Created on Dec 22, 2004
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson Denotes a class that can act as a visitor receiving
 *         double-dispatch methods.
 */
public interface TypeVisitor
{

	public void handleDataObjectType(DataObjectType type);

	public void handleNumericAttribute(NumericAttribute attr);

	public void handleStringAttribute(StringAttribute attr);

	public void handleBooleanAttribute(/* BooleanAttribute attr */);

	public void handleDateAttribute(DateAttribute attr);

	public void handleTimestampAttribute(/* TimestampAttribute */);

	public void handleLovAttribute(LovAttribute attr);

}