/*
 * Created on May 3, 2005
 */
package net.sf.ipn.app;

import wicket.model.LoadableDetachableModel;

/**
 * @author Jonathan Carlson
 */
public abstract class SavableLoadableModel extends LoadableDetachableModel
{

	/**
	 * @see net.sf.ipn.app.SavableLoadableModel#save()
	 */
	public abstract void save();

}
