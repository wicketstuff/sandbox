/*
 * Created on Dec 21, 2004
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson Provides information on a relationship to another type
 */
public interface Relationship
{
	public String getName();

	public boolean isToMany();

	public boolean isToOne();

	/**
	 * For To-Many relationhips this means at least one is required. For To-One
	 * relationships, it means one and only one.
	 * @return true if there must be at least one target instance.
	 */
	public boolean isRequired();

	public Type getTarget();

}
