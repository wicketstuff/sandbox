/*
 * Created on Feb 7, 2005
 */
package net.sf.ipn.dynweb.data;

/**
 * @author Jonathan Carlson TODO Provides...
 */
public interface DataService
{

	public void getInstance(String type, Object primaryKey);

	public void getRelationship(Object model, String property);

}
