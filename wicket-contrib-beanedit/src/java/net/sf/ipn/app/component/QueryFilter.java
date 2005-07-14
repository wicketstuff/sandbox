/*
 * Created on May 23, 2005
 */
package net.sf.ipn.app.component;

import java.io.Serializable;

import org.objectstyle.cayenne.exp.Expression;


/**
 * Provides a mechanism for a UI component (like a DropDownChoice) to inform a
 * SpecializableCayenneQueryModel of changes to filtering.
 * @author Jonathan Carlson
 */
public interface QueryFilter extends Serializable
{
	/**
	 * Returns false if this filter should do no filtering.
	 * @return a Cayenne query qualifier that is an instance of Expression
	 */
	public Expression getQualifier();

	/**
	 * Returns a discrete value (not a list) that limits what is shown in the table of
	 * Cayenne data objects. If nothing is to be filtered, the value of "Any" will be
	 * returned.
	 * @return a String or a data object with a meaningful toString() methood.
	 */
	public Object getFilterValue();

	/**
	 * Sets a discrete value that limits what is shown in the list of Cayenne data
	 * objects. If nothing is to be filtered, this will return "Any".
	 */
	public void setFilterValue(Object o);

}