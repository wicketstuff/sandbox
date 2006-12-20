/*
 * Created on May 23, 2005
 */
package net.sf.ipn.app.component;

import java.io.Serializable;

import org.objectstyle.cayenne.query.Ordering;

/**
 * Provides a mechanism for a UI component (like a Link on a table header) to inform a
 * SpecializableCayenneQueryModel of changes to the query ordering (AKA query sorting).
 * @author Jonathan Carlson
 */
public interface QueryOrdering extends Serializable
{
	/**
	 * Returns a Cayenne query Ordering or null if no ordering should be done.
	 * @return
	 */
	public Ordering getOrdering();

	/**
	 * Should inform the model to place this ordering at the front of the list as well as
	 * turning "on" this ordering.
	 */
	public void onClick();

}