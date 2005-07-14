/*
 * Created on Dec 21, 2004
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson Provides metadata information on an attribute
 */
public interface Attribute extends Type
{

	public boolean isEditable();

	public boolean isDisplayable();

	public boolean isPrimaryKey();

	public boolean isRequired();

}
