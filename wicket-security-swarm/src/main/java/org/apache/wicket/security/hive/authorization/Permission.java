/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.authorization;

import org.apache.wicket.Application;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.security.swarm.actions.SwarmActionFactory;


/**
 * Abstract class for representing access to a system resource.
 * All permissions have a name (whose interpretation depends on the subclass),
 * as well as abstract functions for defining the semantics of the
 * particular Permission subclass. 
 * 
 * <p>Most Permission objects also include an "actions" list that tells the actions 
 * that are permitted for the object.  For example, 
 * for a <code>java.io.FilePermission</code> object, the permission name is
 * the pathname of a file (or directory), and the actions list
 * (such as "read, write") specifies which actions are granted for the
 * specified file (or for files in the specified directory).
 * The actions list is optional for Permission objects, such as 
 * <code>java.lang.RuntimePermission</code>,
 * that don't need such a list; you either have the named permission (such
 * as "system.exit") or you don't.
 * 
 * <p>An important method that must be implemented by each subclass is
 * the <code>implies</code> method to compare Permissions. Basically,
 * "permission p1 implies permission p2" means that
 * if one is granted permission p1, one is naturally granted permission p2.
 * Thus, this is not an equality test, but rather more of a
 * subset test.
 * 
 * <P> Permission objects are similar to String objects in that they
 * are immutable once they have been created. Subclasses should not
 * provide methods that can change the state of a permission
 * once it has been created.
 * @author marrink
 *
 */
public abstract class Permission
{
	private final String name;
	
	/**
	 * Constructs a permission with a certain name.
	 * @param name
	 */
	public Permission(String name)
	{
		this.name = name;
		
	}
	/**
	 * Check if this permission implies the specified permission.
	 * Default returns false;
	 * @param permission
	 * @return true if this implies the permission, false otherwise. 
	 */
	public abstract boolean implies(Permission permission);
	 /**
     * Checks two Permission objects for equality.
     * <P>
     * Do not use the <code>equals</code> method for making access control
     * decisions; use the <code>implies</code> method.
     *  
     * @param obj the object we are testing for equality with this object.
     *
     * @return true if both Permission objects are equivalent.
     */

    public abstract boolean equals(Object obj);

    /**
     * Returns the hash code value for this Permission object.
     * <P>
     * The required <code>hashCode</code> behavior for Permission Objects is
     * the following: <p>
     * <ul>
     * <li>Whenever it is invoked on the same Permission object more than 
     *     once during an execution of a Java application, the 
     *     <code>hashCode</code> method
     *     must consistently return the same integer. This integer need not 
     *     remain consistent from one execution of an application to another 
     *     execution of the same application. <p>
     * <li>If two Permission objects are equal according to the 
     *     <code>equals</code> 
     *     method, then calling the <code>hashCode</code> method on each of the
     *     two Permission objects must produce the same integer result. 
     * </ul>
     *
     * @return a hash code value for this object.
     */

    public abstract int hashCode();
    /**
     * Returns the actions as a String. This is abstract
     * so subclasses can defer creating a String representation until 
     * one is needed. Subclasses should always return actions in what they
     * consider to be their
     * canonical form. For example, two FilePermission objects created via
     * the following:
     * 
     * <pre>
     *   perm1 = new FilePermission(p1,"read,write");
     *   perm2 = new FilePermission(p2,"write,read"); 
     * </pre>
     * 
     * both return 
     * "read,write" when the <code>getActions</code> method is invoked.
     *
     * @return the actions of this Permission.
     *
     */

    public abstract String getActions();
    /**
     * Returns the name of this Permission.
     * For example, in the case of a <code>org.apache.wicket.security.hive.authorization.permissions.ComponentPermission</code>,
     * the name will be a component path.
     *
     * @return the name of this Permission.
     * 
     */
	public final String getName()
	{
		return name;
	}
	/**
	 * Utility method for getting an action.
	 * @param action the action class
	 * @return the action
	 * @see ActionFactory#getAction(Class)
	 */
	protected static final SwarmAction getAction(Class action)
	{
		return (SwarmAction)((WaspApplication)Application.get()).getActionFactory().getAction(action);
	}
	/**
	 * Utility method for getting an action.
	 * @param actions a bitwise or of the actions
	 * @return the action
	 * @see SwarmActionFactory#getAction(int)
	 */
	protected static final SwarmAction getAction(int actions)
	{
		return ((SwarmActionFactory)((WaspApplication)Application.get()).getActionFactory()).getAction(actions);
	}
	/**
	 * Utility method for getting an action.
	 * @param actions the name of an action
	 * @return the action
	 * @see ActionFactory#getAction(String)
	 */
	protected static final SwarmAction getAction(String actions)
	{
		return (SwarmAction)((WaspApplication)Application.get()).getActionFactory().getAction(actions);
	}
	
}
